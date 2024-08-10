package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.UCI_DTO;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.UserCocktailIngredient;
import cocktailrecommender.backend.repository.UserCocktailIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCocktailIngredientService {
    private final UserCocktailIngredientRepository userCocktailIngredientRepository;
    private final UserService userService;
    private final CocktailService cocktailService;
    private final IngredientService ingredientService;

    @Autowired
    public UserCocktailIngredientService(UserCocktailIngredientRepository userCocktailIngredientRepository, UserService userService,
                                         CocktailService cocktailService, IngredientService ingredientService) {
        this.userCocktailIngredientRepository = userCocktailIngredientRepository;
        this.userService = userService;
        this.cocktailService = cocktailService;
        this.ingredientService = ingredientService;
    }
    //return false if cocktail exists or ingredient not exists.
    public boolean createCocktailWithIngredient(UCI_DTO.CreateUCIDTO createUCIDTO) {
        CocktailDTO.CocktailDTOWithoutId cocktailDTOWithoutId = createUCIDTO.getCocktailDTOWithoutId();

        //if create success(new cocktail name)
        if (cocktailService.createCocktail(cocktailDTOWithoutId)) {
            //guaranteed that cocktail with this name exists.
            CocktailDTO.CocktailDTOWithId cocktailDTOWithId = cocktailService.findCocktailByName(cocktailDTOWithoutId.getName()).get();

            return createUCIDTO.getIngredientAmountDTOList().stream()
                    .allMatch(ia -> ingredientService.findIngredientByName(ia.getIngredientName())
                            .map(ingredient -> {
                                UserCocktailIngredient userCocktailIngredient = new UserCocktailIngredient();
                                userCocktailIngredient.setUser(userService.findById(createUCIDTO.getUserId()).to());
                                userCocktailIngredient.setCocktail(cocktailDTOWithId.to());
                                userCocktailIngredient.setIngredient(ingredient.to());
                                userCocktailIngredient.setAmount(ia.getAmount());
                                userCocktailIngredientRepository.save(userCocktailIngredient);
                                return true;
                            })
                            .orElse(false)
                    );
        }
        return false;
    }

    public List<CocktailDTO.CocktailDTOWithId> findCocktailsByIngredients(List<IngredientDTO> ingredientDTOList, Long userId){
        //Map IngredientDTOList to List<Long> IngredientIds
        List<Cocktail> cocktailList =  userCocktailIngredientRepository.findCocktailsByIngredientsAndUser(
                ingredientDTOList.stream()
                        .map(IngredientDTO::getId)
                        .collect(Collectors.toList()),
                userId
        );
        //return with DTO type
        return cocktailList.stream().map(CocktailDTO.CocktailDTOWithId::from).collect(Collectors.toList());
    }

}
