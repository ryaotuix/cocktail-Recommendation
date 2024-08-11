package cocktailrecommender.backend.controller;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.CocktailIngredientDTO;
import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.User;
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
    private CocktailIngredientService CI_Service;

    @Autowired
    private CocktailTasteService CT_Service;

    @Autowired
    private UserCocktailService CU_Service;

    @Autowired
    private UserService U_Service;

    @Autowired
    private final CocktailRepository cocktailRepository;

    public CocktailController(JwtCertificate jwtCertificate, CocktailRepository cocktailRepository) {
        this.jwtCertificate = jwtCertificate;
        this.cocktailRepository = cocktailRepository;
    }

    @Getter
    public static class CreateDTO
    {
        List<CocktailIngredientDTO.IngredientAmountDTO> ingredientAmountDTOList;
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

        CocktailDTO.CocktailDTOWithId cwithID = C_Service.findByID(cocktailID);
        UserDTO.UserResponseDTO userResponseDTO = U_Service.getUserFromToken(token);

        // 2. add to CU repo
        CU_Service.createUserCocktail(userResponseDTO, cwithID);

        CocktailIngredientDTO.CreateCIDTO createCIDTO = new CocktailIngredientDTO.CreateCIDTO(cwithID, createDTO.getIngredientAmountDTOList());
        // 3. add to CI repo
        if (CI_Service.createCocktailWithIngredient(createCIDTO))
        {
            return ResponseEntity.ok("New Cocktail Created");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cocktail not created");
    }
}
