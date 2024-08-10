package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.CocktailIngredientDTO;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.CocktailIngredient;
import cocktailrecommender.backend.repository.CocktailIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return createCIDTO.getIngredientAmountDTOList().stream()
                .allMatch(ia -> ingredientService.findIngredientByName(ia.getIngredientName())
                        .map(ingredient -> {
                            CocktailIngredient cocktailIngredient = new CocktailIngredient();
                            cocktailIngredient.setCocktail(createCIDTO.getCocktailDTOWithId().to());
                            cocktailIngredient.setIngredient(ingredient.to());
                            cocktailIngredient.setAmount(ia.getAmount());
                            return true;
                        })
                        .orElse(false)
                );
    }

    public List<CocktailDTO.CocktailDTOWithId> findCocktailsByIngredients(List<IngredientDTO> ingredientDTOList){
        //Map IngredientDTOList to List<Long> IngredientIds
        List<Cocktail> cocktailList =  cocktailIngredientRepository.findCocktailsByIngredients(
                ingredientDTOList.stream()
                        .map(IngredientDTO::getId)
                        .collect(Collectors.toList())
        );
        //return with DTO type
        return cocktailList.stream().map(CocktailDTO.CocktailDTOWithId::from).collect(Collectors.toList());
    }
}
