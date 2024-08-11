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
import java.util.Optional;

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
        CocktailDTO.CocktailDTOWithId cocktailDTOWithId = createCIDTO.getCocktailDTOWithId();
        List<CocktailIngredientDTO.IngredientAmountDTO> ingredientAmountDTOList = createCIDTO.getIngredientAmountDTOList();

        // Step 1:
        Optional<CocktailDTO.CocktailDTOWithId> optionalCocktail = cocktailService.findCocktailByName(cocktailDTOWithId.getName());
        if (!optionalCocktail.isPresent()) {
            // Cocktail doesn't exist in C-Repo, return false
            return false;
        }

        List<CocktailIngredient> cocktailIngredientList = List.of();

        // Step 2:
        for (CocktailIngredientDTO.IngredientAmountDTO ingredientAmountDTO : ingredientAmountDTOList) {
            IngredientDTO ingredientDTO = ingredientAmountDTO.getIngredient();

            Optional<IngredientDTO> optionalIngredient = ingredientService.findIngredientByName(ingredientDTO.getName());

            if (!optionalIngredient.isPresent()) {
                // ingredient doesn't exist in I-Repo, return false
                return false;
            }

            // Step 3: Create a new CocktailIngredient entry
            CocktailIngredient cocktailIngredient = new CocktailIngredient();
            cocktailIngredient.setCocktail(cocktailDTOWithId.to());
            cocktailIngredient.setIngredient(optionalIngredient.get().to());
            cocktailIngredient.setAmount(ingredientAmountDTO.getAmount());
            cocktailIngredient.setUnit(ingredientAmountDTO.getUnit());

            // add to List
            cocktailIngredientList.add(cocktailIngredient);
        }

        // add to C-I only when it is true
        cocktailIngredientRepository.saveAll(cocktailIngredientList);

        return true;
    }


//    // 칵테일 재료 수정
//    /*
//        로직 : if ciDTO 's Cocktail exist in C-repository, (which means it exists in C-I repo), remove all C-I with this cocktail
//        and rewrite all C-I
//    */
//    public boolean fixCocktailIngredientInfo(CocktailIngredientDTO.UpdateCIDTO updateCIDTO) {
//        // UpdateCIDTO에서 정보를 추출
//        Long cocktailId = updateCIDTO.getCocktailId();
//        String ingredientName = updateCIDTO.getIngredientName();
//        Double newAmount = updateCIDTO.getNewAmount();
//
//        // 칵테일과 재료를 찾기
//        Cocktail cocktail = cocktailService.findCocktailById(cocktailId)
//                .orElseThrow(() -> new IllegalStateException("Cocktail not found."));
//
//        // 재료를 찾기
//        return ingredientService.findIngredientByName(ingredientName)
//                .map(ingredient -> {
//                    // CocktailIngredient를 찾아서 업데이트
//                    CocktailIngredient cocktailIngredient = cocktailIngredientRepository.findByCocktailAndIngredient(cocktail, ingredient)
//                            .orElseThrow(() -> new IllegalStateException("CocktailIngredient not found."));
//
//                    cocktailIngredient.setAmount(newAmount);
//
//                    // 업데이트된 정보를 저장
//                    cocktailIngredientRepository.save(cocktailIngredient);
//                    return true;
//                })
//                .orElse(false); // 재료가 존재하지 않으면 false 반환
//    }
//
//
//    // 재료로 칵테일 검색
//    public List<Cocktail> searchCocktailByIngredient(List<IngredientDTO> ingredientDTOList) {
//        // 재료를 찾기
//        return ingredientService.findIngredientByName(ingredientName)
//                .map(ingredient -> cocktailIngredientRepository.findByIngredient(ingredient))
//                .map(cocktailIngredients -> cocktailIngredients.stream()
//                        .map(CocktailIngredient::getCocktail)
//                        .distinct()
//                        .collect(Collectors.toList())
//                )
//                .orElse(Collections.emptyList()); // 재료가 존재하지 않으면 빈 리스트 반환
//    }


}
