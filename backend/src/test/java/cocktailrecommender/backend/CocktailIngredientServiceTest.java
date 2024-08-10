package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.CocktailIngredientDTO;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.CocktailIngredient;
import cocktailrecommender.backend.domain.Ingredient;
import cocktailrecommender.backend.repository.CocktailIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CocktailIngredientServiceTest {

    @Mock
    private CocktailIngredientRepository cocktailIngredientRepository;

    @Mock
    private CocktailService cocktailService;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private CocktailIngredientService cocktailIngredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCocktailWithIngredient_Success() {
        // Arrange
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Lime Juice");
        CocktailIngredientDTO.IngredientAmountDTO ingredientAmountDTO = new CocktailIngredientDTO.IngredientAmountDTO("Lime Juice", 2);
        CocktailIngredientDTO.CreateCIDTO createCIDTO = new CocktailIngredientDTO.CreateCIDTO(
                new CocktailDTO.CocktailDTOWithId(1L, "Mojito", "Mix ingredients"),
                Arrays.asList(ingredientAmountDTO)
        );

        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1L);
        ingredient.setName("Lime Juice");

        when(ingredientService.findIngredientByName("Lime Juice")).thenReturn(Optional.of(ingredient));

        // Act
        boolean result = cocktailIngredientService.createCocktailWithIngredient(createCIDTO);

        // Assert
        assertTrue(result);
        verify(ingredientService, times(1)).findIngredientByName("Lime Juice");
    }

    @Test
    void testCreateCocktailWithIngredient_Failure_NoIngredient() {
        // Arrange
        CocktailIngredientDTO.IngredientAmountDTO ingredientAmountDTO = new CocktailIngredientDTO.IngredientAmountDTO("Nonexistent Ingredient", "1 oz");
        CocktailIngredientDTO.CreateCIDTO createCIDTO = new CocktailIngredientDTO.CreateCIDTO(
                new CocktailDTO.CocktailDTOWithId(1L, "Mojito", "Mix ingredients"),
                Arrays.asList(ingredientAmountDTO)
        );

        when(ingredientService.findIngredientByName("Nonexistent Ingredient")).thenReturn(Optional.empty());

        // Act
        boolean result = cocktailIngredientService.createCocktailWithIngredient(createCIDTO);

        // Assert
        assertFalse(result);
        verify(ingredientService, times(1)).findIngredientByName("Nonexistent Ingredient");
    }

    @Test
    void testFindCocktailsByIngredients() {
        // Arrange
        IngredientDTO ingredientDTO1 = new IngredientDTO(1L, "Lime Juice");
        IngredientDTO ingredientDTO2 = new IngredientDTO(2L, "Mint Leaves");
        List<IngredientDTO> ingredientDTOList = Arrays.asList(ingredientDTO1, ingredientDTO2);

        Cocktail cocktail1 = new Cocktail();
        cocktail1.setCocktailId(1L);
        cocktail1.setName("Mojito");
        cocktail1.setHowToMake("Mix ingredients");

        when(cocktailIngredientRepository.findCocktailsByIngredients(anyList())).thenReturn(Arrays.asList(cocktail1));

        // Act
        List<CocktailDTO.CocktailDTOWithId> result = cocktailIngredientService.findCocktailsByIngredients(ingredientDTOList);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Mojito", result.get(0).getName());
        verify(cocktailIngredientRepository, times(1)).findCocktailsByIngredients(anyList());
    }
}
