package cocktailrecommender.backend;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.repository.CocktailRepository;
import cocktailrecommender.backend.service.CocktailService;
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

class CocktailServiceTest {

    @Mock
    private CocktailRepository cocktailRepository;

    @InjectMocks
    private CocktailService cocktailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCocktail() {
        // Given: Margarita DTO 및 Mojito 객체를 생성합니다.
        CocktailDTO.CocktailDTOWithoutId margaritaDTO = new CocktailDTO.CocktailDTOWithoutId();
        margaritaDTO.setName("Margarita");
        margaritaDTO.setHowToMake("Mix all ingredients and serve over ice.");

        Cocktail margarita = new Cocktail();
        margarita.setCocktailId(1L);
        margarita.setName("Margarita");
        margarita.setHowToMake("Mix all ingredients and serve over ice.");

        CocktailDTO.CocktailDTOWithoutId mojitoDTO = new CocktailDTO.CocktailDTOWithoutId();
        mojitoDTO.setName("Mojito");
        mojitoDTO.setHowToMake("Mix all ingredients and serve over ice.");

        Cocktail mojito = new Cocktail();
        mojito.setCocktailId(2L);
        mojito.setName("Mojito");
        mojito.setHowToMake("Mix all ingredients and serve over ice.");

        // When: 이미 Margarita가 저장되어 있다고 가정합니다.
        when(cocktailRepository.findByName("Margarita")).thenReturn(Optional.of(margarita));
        when(cocktailRepository.findByName("Mojito")).thenReturn(Optional.empty());

        when(cocktailRepository.save(any(Cocktail.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Then: 중복된 이름의 칵테일 생성 시도를 확인합니다.
        boolean checkDuplicate = cocktailService.createCocktail(margaritaDTO);
        assertFalse(checkDuplicate); // Margarita는 이미 존재하므로 false 여야 합니다.

        // Then: 새로운 칵테일 생성 시도를 확인합니다.
        boolean checkNew = cocktailService.createCocktail(mojitoDTO);
        assertTrue(checkNew); // Mojito는 존재하지 않으므로 true 여야 합니다.

        // Verify: save() 메소드가 Margarita에는 호출되지 않고 Mojito에는 호출된 것을 확인합니다.
        verify(cocktailRepository, times(0)).save(margarita);
        verify(cocktailRepository, times(1)).save(any(Cocktail.class));
    }


    @Test
    void testFindCocktailByName() {
        Cocktail cocktail = new Cocktail();
        cocktail.setCocktailId(1L);
        cocktail.setName("Margarita");
        cocktail.setHowToMake("Mix all ingredients and serve over ice.");

        when(cocktailRepository.findByName("Margarita")).thenReturn(Optional.of(cocktail));

        Optional<CocktailDTO.CocktailDTOWithId> result = cocktailService.findCocktailByName("Margarita");

        assertTrue(result.isPresent());
        assertEquals("Margarita", result.get().getName());
        verify(cocktailRepository, times(1)).findByName("Margarita");
    }

    @Test
    void testFindCocktailsByName() {
        Cocktail cocktail1 = new Cocktail();
        cocktail1.setCocktailId(1L);
        cocktail1.setName("Margarita");
        cocktail1.setHowToMake("Mix all ingredients and serve over ice.");

        Cocktail cocktail2 = new Cocktail();
        cocktail2.setCocktailId(2L);
        cocktail2.setName("Martini");
        cocktail2.setHowToMake("Stir ingredients with ice and strain.");

        when(cocktailRepository.findByNameContainingIgnoreCase("Mar"))
                .thenReturn(Arrays.asList(cocktail1, cocktail2));

        List<CocktailDTO.CocktailDTOWithId> result = cocktailService.findCocktailsByName("Mar");

        assertEquals(2, result.size());
        assertEquals("Margarita", result.get(0).getName());
        assertEquals("Martini", result.get(1).getName());
        verify(cocktailRepository, times(1)).findByNameContainingIgnoreCase("Mar");
    }

    @Test
    void testEditHowToMake() {
        Cocktail cocktail = new Cocktail();
        cocktail.setCocktailId(1L);
        cocktail.setName("Margarita");
        cocktail.setHowToMake("Mix all ingredients and serve over ice.");

        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));

        boolean result = cocktailService.editHowToMake(1L, "Shake and strain into a glass.");

        assertTrue(result);
        assertEquals("Shake and strain into a glass.", cocktail.getHowToMake());
        verify(cocktailRepository, times(1)).save(cocktail);
    }

    @Test
    void testDeleteCocktailUsingFindByName() {
        // Step 1: Create a cocktail object
        Cocktail margarita = new Cocktail();
        margarita.setCocktailId(1L);
        margarita.setName("Margarita");
        margarita.setHowToMake("Mix all ingredients and serve over ice.");

        // Step 2: Mock the findByNameContainingIgnoreCase to return the created cocktail
        when(cocktailRepository.findByNameContainingIgnoreCase("Margarita"))
                .thenReturn(List.of(margarita));

        // Step 3: Verify that the cocktail is found by name
        List<CocktailDTO.CocktailDTOWithId> foundCocktails = cocktailService.findCocktailsByName("Margarita");
        assertEquals(1, foundCocktails.size());
        assertEquals("Margarita", foundCocktails.get(0).getName());

        // Step 4: Mock the findById method for deletion
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(margarita));

        // Step 5: Call the delete method
        boolean result = cocktailService.deleteCocktail(1L);

        // Step 6: Verify the deletion
        assertTrue(result);
        verify(cocktailRepository, times(1)).deleteById(1L);

        // Step 7: Mock findByNameContainingIgnoreCase to return an empty list after deletion
        when(cocktailRepository.findByNameContainingIgnoreCase("Margarita"))
                .thenReturn(List.of());

        // Step 8: Verify that the cocktail is no longer found by name
        List<CocktailDTO.CocktailDTOWithId> emptyCocktailList = cocktailService.findCocktailsByName("Margarita");
        assertTrue(emptyCocktailList.isEmpty());
    }
}
