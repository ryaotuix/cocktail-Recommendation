package cocktailrecommender.backend;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.repository.CocktailRepository;
import cocktailrecommender.backend.service.CocktailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CocktailServiceTest {
    @Autowired
    private CocktailService cocktailService;
    @Autowired
    private CocktailRepository cocktailRepository;

    @AfterEach
    void clear(){
        cocktailRepository.deleteAll();
    }
    @Test
    void createCocktail(){
        assertTrue(cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake")));
        assertFalse(cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake")));
        assertTrue(cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail2","howToMake")));
    }
    @Test
    void findCocktails(){
        cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake"));
        cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail2","howToMake"));
        int idx=1;
        for(CocktailDTO.CocktailDTOWithId dto: cocktailService.findCocktailsByName("cocktail")){
            assertEquals("cocktail"+ idx++,dto.getName());
        }
        assertEquals("cocktail1",cocktailService.findCocktailByName("cocktail1").get().getName());
    }

    @Test
    void deleteCocktail(){
        cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail1","howToMake"));
        cocktailService.createCocktail(new CocktailDTO.CocktailDTOWithoutId("cocktail2","howToMake"));
        assertTrue(cocktailService.deleteCocktail(cocktailService.findCocktailByName("cocktail1").get().getCocktailId()));
        assertEquals("cocktail2",cocktailRepository.findAll().get(0).getName());
    }
}
