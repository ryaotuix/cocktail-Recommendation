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

    // 칵테일 생성
    // Return false if the cocktail already exists or if any ingredient does not exist
    public boolean createCocktailWithIngredient(CocktailIngredientDTO.CreateCIDTO createCIDTO) {
        CocktailDTO.CocktailDTOWithoutId cocktailDTOWithoutId = createCIDTO.getCocktailDTOWithoutId();

        // Attempt to create the cocktail. If it already exists, return false
        if (cocktailService.createCocktail(cocktailDTOWithoutId)) {
            // Find the created cocktail by name (it must exist at this point)
            CocktailDTO.CocktailDTOWithId cocktailDTOWithId = cocktailService.findCocktailByName(cocktailDTOWithoutId.getName())
                    .orElseThrow(() -> new IllegalStateException("Cocktail creation failed, but cocktail still returned null."));

            // Process each ingredient and create CocktailIngredient entries
            return createCIDTO.getIngredientAmountDTOList().stream()
                    .allMatch(ia -> ingredientService.findIngredientByName(ia.getIngredientName())
                            .map(ingredient -> {
                                CocktailIngredient cocktailIngredient = new CocktailIngredient();
                                cocktailIngredient.setCocktail(cocktailDTOWithId.to());
                                cocktailIngredient.setIngredient(ingredient.to());
                                cocktailIngredient.setAmount(ia.getAmount());

                                // Save the CocktailIngredient
                                cocktailIngredientRepository.save(cocktailIngredient);
                                return true;
                            })
                            .orElse(false)  // If any ingredient is not found, return false
                    );
        }

        // If the cocktail could not be created (it already exists), return false
        return false;
    }

    // 칵테일 재료 수정
    public boolean fixCocktailIngredientInfo(CocktailIngredientDTO.UpdateCIDTO updateCIDTO) {
        // UpdateCIDTO에서 정보를 추출
        Long cocktailId = updateCIDTO.getCocktailId();
        String ingredientName = updateCIDTO.getIngredientName();
        Double newAmount = updateCIDTO.getNewAmount();

        // 칵테일과 재료를 찾기
        Cocktail cocktail = cocktailService.findCocktailById(cocktailId)
                .orElseThrow(() -> new IllegalStateException("Cocktail not found."));

        // 재료를 찾기
        return ingredientService.findIngredientByName(ingredientName)
                .map(ingredient -> {
                    // CocktailIngredient를 찾아서 업데이트
                    CocktailIngredient cocktailIngredient = cocktailIngredientRepository.findByCocktailAndIngredient(cocktail, ingredient)
                            .orElseThrow(() -> new IllegalStateException("CocktailIngredient not found."));

                    cocktailIngredient.setAmount(newAmount);

                    // 업데이트된 정보를 저장
                    cocktailIngredientRepository.save(cocktailIngredient);
                    return true;
                })
                .orElse(false); // 재료가 존재하지 않으면 false 반환
    }


    // 재료로 칵테일 검색
    public List<Cocktail> searchCocktailByIngredient(List<IngredientDTO> ingredientDTOList) {
        // 재료를 찾기
        return ingredientService.findIngredientByName(ingredientName)
                .map(ingredient -> cocktailIngredientRepository.findByIngredient(ingredient))
                .map(cocktailIngredients -> cocktailIngredients.stream()
                        .map(CocktailIngredient::getCocktail)
                        .distinct()
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList()); // 재료가 존재하지 않으면 빈 리스트 반환
    }


}
