package cocktailrecommender.backend.controller;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.UCI_DTO;
import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.repository.CocktailRepository;
import cocktailrecommender.backend.service.*;
import cocktailrecommender.backend.utils.JwtCertificate;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cocktail")
public class CocktailController {
    private final JwtCertificate jwtCertificate;

    @Autowired
    private CocktailService C_Service;

    @Autowired
    private UCIService UCI_Service;

    @Autowired
    private CocktailTasteService CT_Service;

    @Autowired
    private UserCocktailService CU_Service;

    @Autowired
    private UserService U_Service;


    public CocktailController(JwtCertificate jwtCertificate, CocktailRepository cocktailRepository) {
        this.jwtCertificate = jwtCertificate;
    }


    @Getter
    public static class CreateDTO
    {
        List<UCI_DTO.IngredientAmountDTO> ingredientAmountDTOList;
        CocktailDTO.CocktailDTOWithoutId cocktailDTO;
    }

    /*토큰, cWithoutID, list<IngredientDTO> 를 받음 */
    @PostMapping("/add")
    public ResponseEntity<String> createCocktail(@RequestHeader("Authorization") String token, @RequestBody CreateDTO createDTO)
    {
        // 토큰 제대로 만들어주기
        token = token.replace("Bearer ", "");


        // 1. add to C repo
        Long cocktailID = C_Service.createCocktail(createDTO.getCocktailDTO());
        // cDTO, uDTO
        CocktailDTO.CocktailDTOWithId cwithID = C_Service.findByID(cocktailID);
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        // 2. add to CU repo
        // if this cocktail exist in this user, can't make
        if (CU_Service.cocktailExistForUser(userResponseDTO, cwithID))
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("You already have this cocktail");
        }

        // if this cocktail doesn't exist in this user,
        CU_Service.createUserCocktail(userResponseDTO, cwithID);

        UCI_DTO.Create_UCI_DTO createUCI_DTO = new UCI_DTO.Create_UCI_DTO(userResponseDTO, cwithID, createDTO.getIngredientAmountDTOList());

        // 3. add to UCI repo
        // if
        if (UCI_Service.createCocktailWithIngredient(createUCI_DTO))
        {
            return ResponseEntity.ok("New UCI Created");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cocktail not created");
    }

    /* 토큰과 칵테일 ID를 받아 칵테일을 삭제함 */
    @DeleteMapping("/delete/{cocktailId}")
    public ResponseEntity<String> deleteCocktail(@RequestHeader("Authorization") String token, @PathVariable Long cocktailId) {
        token = token.replace("Bearer ", "");
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        CocktailDTO.CocktailDTOWithId cocktailDTOWithId = C_Service.findByID(cocktailId);

        UCI_DTO.Delete_UCI_DTO deleteUciDto = new UCI_DTO.Delete_UCI_DTO(userResponseDTO, cocktailDTOWithId);

        if (UCI_Service.deleteCocktailIngredient(deleteUciDto)) {
            return ResponseEntity.ok("Cocktail deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete cocktail");
    }

    /* 토큰과 UCI_DTO를 받아 칵테일 재료를 수정함 */
    @PutMapping("/update")
    public ResponseEntity<String> updateCocktail(@RequestHeader("Authorization") String token, @RequestBody UCI_DTO.Create_UCI_DTO createUciDto) {
        token = token.replace("Bearer ", "");
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        // 기존 재료 삭제
        UCI_DTO.Delete_UCI_DTO deleteUciDto = new UCI_DTO.Delete_UCI_DTO(userResponseDTO, createUciDto.getCocktailDTOWithId());
        if (!UCI_Service.deleteCocktailIngredient(deleteUciDto)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cocktail");
        }

        // 새 재료 추가
        if (UCI_Service.createCocktailWithIngredient(createUciDto)) {
            return ResponseEntity.ok("Cocktail updated successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cocktail");
    }


}
