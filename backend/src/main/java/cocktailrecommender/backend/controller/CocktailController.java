package cocktailrecommender.backend.controller;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.service.CocktailIngredientService;
import cocktailrecommender.backend.service.CocktailService;
import cocktailrecommender.backend.service.CocktailTasteService;
import cocktailrecommender.backend.service.UserCocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cocktail")
public class CocktailController {
    private final CocktailService C_Service;
    private final CocktailIngredientService CI_Service;
    private final CocktailTasteService CT_Service;
    private final UserCocktailService CU_Service;

    @Autowired
    public CocktailController(CocktailService cService, CocktailIngredientService ciService, CocktailTasteService ctService, UserCocktailService cuService) {
        C_Service = cService;
        CI_Service = ciService;
        CT_Service = ctService;
        CU_Service = cuService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCocktail(@RequestBody CocktailDTO.CocktailDTOWithoutId cocktailDTO)
    {
        // 1. add to cocktail repo

    }
}
