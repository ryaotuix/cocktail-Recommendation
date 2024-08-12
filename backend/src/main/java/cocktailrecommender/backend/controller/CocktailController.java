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

        // 2. if this cocktail exist in this user, can't make
        if (UCI_Service.cocktailExistForUser(userResponseDTO, cwithID))
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("You already have this cocktail");
        }

        // 3. add to UCI repo
        UCI_DTO.Create_UCI_DTO createUCI_DTO = new UCI_DTO.Create_UCI_DTO(userResponseDTO, cwithID, createDTO.getIngredientAmountDTOList());


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

    @Getter
    public static class UpdateDTO
    {
        CocktailDTO.CocktailDTOWithId cocktailDTO;
        String howtoMake;
    }

    // 칵테일 조제과정 수정
    @PutMapping("/update/howToMake")
    public ResponseEntity<String> updateHowToMake(@RequestHeader("Authorization") String token, @RequestBody UpdateDTO updateDTO) {
        token = token.replace("Bearer ", "");
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        String howtoMake = updateDTO.getHowtoMake();

        CocktailDTO.CocktailDTOWithId cocktailDTO = updateDTO.getCocktailDTO();
        // see if this user have this cocktail
        if (!UCI_Service.cocktailExistForUser(userResponseDTO, cocktailDTO))
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to find cocktail for you");
        }

        C_Service.editHowToMake(cocktailDTO.getCocktailId(), howtoMake);
        return ResponseEntity.ok("How to Make is updated successfully");
    }


    // 칵테일 유저가 가진 재료로 검색
    @PostMapping("/searchByYourIngredients")
    public ResponseEntity<List<CocktailDTO.CocktailDTOWithId>> searchCocktailByUserIngredients(@RequestHeader("Authorization") String token, @RequestBody UCI_DTO.UCI_Ingredients_DTO ingredientsDto) {
        token = token.replace("Bearer ", "");
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        // 사용자가 가진 재료로 칵테일 검색
        List<CocktailDTO.CocktailDTOWithId> cocktails = UCI_Service.findCocktailByUserIngredients(ingredientsDto);

        return ResponseEntity.ok(cocktails);
    }


    // 칵테일 유저가 선택한 재료로 검색
    @PostMapping("/searchByIngredients")
    public ResponseEntity<List<CocktailDTO.CocktailDTOWithId>> searchCocktailBySelectedIngredients(@RequestHeader("Authorization") String token, @RequestBody UCI_DTO.UCI_Ingredients_DTO ingredientsDto) {
        token = token.replace("Bearer ", "");
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        // 사용자가 가진 재료로 칵테일 검색
        List<CocktailDTO.CocktailDTOWithId> cocktails = UCI_Service.findCocktailBySelectedIngredients(ingredientsDto);

        return ResponseEntity.ok(cocktails);
    }


}
