package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.CocktailIngredientDTO;
import cocktailrecommender.backend.domain.CocktailIngredient;
import cocktailrecommender.backend.repository.CocktailIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailIngredientService {
    private final CocktailIngredientRepository cocktailIngredientRepository;
    private final CocktailService cocktailService;
    private final IngredientService ingredientService;

    @Autowired
    public CocktailIngredientService(CocktailIngredientRepository cocktailIngredientRepository, CocktailService cocktailService, IngredientService ingredientService) {
        this.cocktailIngredientRepository = cocktailIngredientRepository;
        this.cocktailService = cocktailService;
        this.ingredientService = ingredientService;
    }
    //return false if cocktail exists or ingredient not exists.
    public boolean createCocktailWithIngredient(CocktailIngredientDTO.CreateCIDTO createCIDTO) {
        CocktailDTO.CocktailDTOWithoutId cocktailDTOWithoutId = createCIDTO.getCocktailDTOWithoutId();

        //if create success(new cocktail name)
        if (cocktailService.createCocktail(cocktailDTOWithoutId)) {
            //guaranteed that cocktail with this name exists.
            CocktailDTO.CocktailDTOWithId cocktailDTOWithId = cocktailService.findCocktailByName(cocktailDTOWithoutId.getName()).get();

            return createCIDTO.getIngredientAmountDTOList().stream()
                    .allMatch(ia -> ingredientService.findIngredientByName(ia.getIngredientName())
                            .map(ingredient -> {
                                CocktailIngredient cocktailIngredient = new CocktailIngredient();
                                cocktailIngredient.setCocktail(cocktailDTOWithId.to());
                                cocktailIngredient.setIngredient(ingredient.to());
                                cocktailIngredient.setAmount(ia.getAmount());
                                return true;
                            })
                            .orElse(false)
                    );
        }
        return false;
    }
}
