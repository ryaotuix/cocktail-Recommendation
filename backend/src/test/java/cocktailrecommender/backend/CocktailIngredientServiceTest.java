//package cocktailrecommender.backend;
//
//import cocktailrecommender.backend.DTO.CocktailDTO;
//import cocktailrecommender.backend.DTO.CocktailIngredientDTO;
//import cocktailrecommender.backend.domain.CocktailIngredient;
//import cocktailrecommender.backend.repository.CocktailIngredientRepository;
//import cocktailrecommender.backend.repository.CocktailRepository;
//import cocktailrecommender.backend.repository.IngredientRepository;
//import cocktailrecommender.backend.service.CocktailIngredientService;
//import cocktailrecommender.backend.service.CocktailService;
//import cocktailrecommender.backend.service.IngredientService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class CocktailIngredientServiceTest {
//    @Autowired
//    private CocktailIngredientRepository cocktailIngredientRepository;
//    @Autowired
//    private CocktailIngredientService cocktailIngredientService;
//    @Autowired
//    private IngredientRepository ingredientRepository;
//    @Autowired
//    private IngredientService ingredientService;
//    @Autowired
//    private CocktailRepository cocktailRepository;
//    @Autowired
//    private CocktailService cocktailService;
//    @BeforeEach
//    void setUp() {
//        ingredientService.createIngredient("ingredient1");
//        ingredientService.createIngredient("ingredient2");
//        cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake"));
//    }
//    @AfterEach
//    void clear(){
//        cocktailRepository.deleteAll();
//        ingredientRepository.deleteAll();
//        cocktailIngredientRepository.deleteAll();
//    }
//    @Test
//    void createCI(){
//        CocktailIngredientDTO.IngredientAmountDTO ia1 = new CocktailIngredientDTO.IngredientAmountDTO("ingredient1",1d);
//        CocktailIngredientDTO.IngredientAmountDTO ia2 = new CocktailIngredientDTO.IngredientAmountDTO("ingredient2",2d);
//        List<CocktailIngredientDTO.IngredientAmountDTO> ingredientAmountDTOList = new ArrayList<>();
//        ingredientAmountDTOList.add(ia1); ingredientAmountDTOList.add(ia2);
//
//        CocktailIngredientDTO.CreateCIDTO createCIDTO = new CocktailIngredientDTO.CreateCIDTO();
//        createCIDTO.setCocktailDTOWithoutId(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake"));
//        createCIDTO.setIngredientAmountDTOList(ingredientAmountDTOList);
//
//        //cocktail name exists, return False
//        assertFalse(cocktailIngredientService.createCocktailWithIngredient(createCIDTO));
//
//        //new cocktail name, return True
//        createCIDTO.setCocktailDTOWithoutId(new CocktailDTO.CocktailDTOWithoutId("cocktail2","howToMake"));
//        assertTrue(cocktailIngredientService.createCocktailWithIngredient(createCIDTO));
//
//        //not existing ingredient, return False
//        ingredientAmountDTOList.add(new CocktailIngredientDTO.IngredientAmountDTO("ingredient3",1d));
//        createCIDTO.setIngredientAmountDTOList(ingredientAmountDTOList);
//        assertFalse(cocktailIngredientService.createCocktailWithIngredient(createCIDTO));
//
//
//    }
//
//
//}
