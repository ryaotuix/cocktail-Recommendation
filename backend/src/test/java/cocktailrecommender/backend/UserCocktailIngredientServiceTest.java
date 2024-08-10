package cocktailrecommender.backend;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.UCI_DTO;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.repository.UserCocktailIngredientRepository;
import cocktailrecommender.backend.repository.CocktailRepository;
import cocktailrecommender.backend.repository.IngredientRepository;
import cocktailrecommender.backend.repository.UserRepository;
import cocktailrecommender.backend.service.UserCocktailIngredientService;
import cocktailrecommender.backend.service.CocktailService;
import cocktailrecommender.backend.service.IngredientService;
import cocktailrecommender.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserCocktailIngredientServiceTest {
    @Autowired
    private UserCocktailIngredientRepository userCocktailIngredientRepository;
    @Autowired
    private UserCocktailIngredientService userCocktailIngredientService;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private CocktailService cocktailService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        ingredientService.createIngredient("ingredient1");
        ingredientService.createIngredient("ingredient2");
        //1st user as admin
        userService.createUser(new UserDTO.UserRequestDTO("admin","admin","pw"));
        //cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake"));
    }
    @AfterEach
    void clear(){
        userCocktailIngredientRepository.deleteAll();
        cocktailRepository.deleteAll();
        ingredientRepository.deleteAll();
    }
    @Test
    void createCI(){
        UCI_DTO.IngredientAmountDTO ia1 = new UCI_DTO.IngredientAmountDTO("ingredient1",1d);
        UCI_DTO.IngredientAmountDTO ia2 = new UCI_DTO.IngredientAmountDTO("ingredient2",2d);
        List<UCI_DTO.IngredientAmountDTO> ingredientAmountDTOList = new ArrayList<>();
        ingredientAmountDTOList.add(ia1); ingredientAmountDTOList.add(ia2);

        UCI_DTO.CreateUCIDTO createUCIDTO = new UCI_DTO.CreateUCIDTO();
        createUCIDTO.setCocktailDTOWithoutId(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake"));
        createUCIDTO.setIngredientAmountDTOList(ingredientAmountDTOList);

        //cocktail name exists, return False
        assertFalse(userCocktailIngredientService.createCocktailWithIngredient(createUCIDTO));

        //new cocktail name, return True
        createUCIDTO.setCocktailDTOWithoutId(new CocktailDTO.CocktailDTOWithoutId("cocktail2","howToMake"));
        assertTrue(userCocktailIngredientService.createCocktailWithIngredient(createUCIDTO));

        //not existing ingredient, return False
        ingredientAmountDTOList.add(new UCI_DTO.IngredientAmountDTO("ingredient3",1d));
        createUCIDTO.setIngredientAmountDTOList(ingredientAmountDTOList);
        assertFalse(userCocktailIngredientService.createCocktailWithIngredient(createUCIDTO));
    }
    @Test
    void findCocktails(){
        ingredientService.createIngredient("ingredient3");
        ingredientService.createIngredient("ingredient4");


        //=============CREATE COCKTAIL=================

        //set IngredientAmountDTO
        UCI_DTO.IngredientAmountDTO ia1 = new UCI_DTO.IngredientAmountDTO("ingredient1",1d);
        UCI_DTO.IngredientAmountDTO ia2 = new UCI_DTO.IngredientAmountDTO("ingredient2",2d);
        List<UCI_DTO.IngredientAmountDTO> ingredientAmountDTOList = new ArrayList<>();
        ingredientAmountDTOList.add(ia1); ingredientAmountDTOList.add(ia2);

        //set UCI DTO
        UCI_DTO.CreateUCIDTO createUCIDTO = new UCI_DTO.CreateUCIDTO();
        createUCIDTO.setUserId(1L);
        createUCIDTO.setCocktailDTOWithoutId(new CocktailDTO.CocktailDTOWithoutId("cocktail2","howToMake"));
        createUCIDTO.setIngredientAmountDTOList(ingredientAmountDTOList);

        userCocktailIngredientService.createCocktailWithIngredient(createUCIDTO);


        //set IngredientAmountDTO
        UCI_DTO.IngredientAmountDTO ia3 = new UCI_DTO.IngredientAmountDTO("ingredient1",1d);
        UCI_DTO.IngredientAmountDTO ia4 = new UCI_DTO.IngredientAmountDTO("ingredient2",2d);
        UCI_DTO.IngredientAmountDTO ia5 = new UCI_DTO.IngredientAmountDTO("ingredient3",2d);
        List<UCI_DTO.IngredientAmountDTO> ingredientAmountDTOList2 = new ArrayList<>();
        ingredientAmountDTOList.add(ia3); ingredientAmountDTOList.add(ia4); ingredientAmountDTOList.add(ia5);

        //set UCI DTO
        createUCIDTO.setUserId(1L);
        createUCIDTO.setCocktailDTOWithoutId(new CocktailDTO.CocktailDTOWithoutId("cocktail3","howToMake"));
        createUCIDTO.setIngredientAmountDTOList(ingredientAmountDTOList2);

        userCocktailIngredientService.createCocktailWithIngredient(createUCIDTO);


        //=============SEARCH COCKTAIL=================
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        ingredientDTOList.add(ingredientService.findIngredientByName("ingredient1").get());
        ingredientDTOList.add(ingredientService.findIngredientByName("ingredient2").get());
        ingredientDTOList.add(ingredientService.findIngredientByName("ingredient3").get());

        int idx=2;
        List<CocktailDTO.CocktailDTOWithId> cocktailDTOWithIds =  userCocktailIngredientService.findCocktailsByIngredients(ingredientDTOList,1L);
        for(CocktailDTO.CocktailDTOWithId ck: cocktailDTOWithIds){
            assertEquals("cocktail"+idx++,ck.getName());
        }




    }


}
