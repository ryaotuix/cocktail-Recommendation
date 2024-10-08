package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.UCI_DTO;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.UCI;
import cocktailrecommender.backend.repository.UCIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UCIService {
    private final UCIRepository UCIRepository;
    private final CocktailService cocktailService;
    private final IngredientService ingredientService;

    @Autowired
    public UCIService(cocktailrecommender.backend.repository.UCIRepository uciRepository, CocktailService cocktailService, IngredientService ingredientService) {
        UCIRepository = uciRepository;
        this.cocktailService = cocktailService;
        this.ingredientService = ingredientService;
    }

    // check if this cocktail exists for user
    public boolean cocktailExistForUser(UserDTO.UserResponseDTO userResponseDTO, CocktailDTO.CocktailDTOWithId cocktailDTOWithId)
    {
        Long userId = userResponseDTO.getUserId();
        Long cocktailId = cocktailDTOWithId.getCocktailId();
        return UCIRepository.existsByUserIdAndCocktailId(userId, cocktailId);
    }

    // U-C-I relationship
    // 칵테일 생성
    // Return false if the cocktail already exists or if any ingredient does not exist
    public boolean createCocktailWithIngredient(UCI_DTO.Create_UCI_DTO createUCI_DTO) {

        UserDTO.UserResponseDTO userResponseDTO = createUCI_DTO.getUserResponseDTO();
        CocktailDTO.CocktailDTOWithId cocktailDTOWithId = createUCI_DTO.getCocktailDTOWithId();
        List<UCI_DTO.IngredientAmountDTO> ingredientAmountDTOList = createUCI_DTO.getIngredientAmountDTOList();

//        // Step 1:
//        Optional<CocktailDTO.CocktailDTOWithId> optionalCocktail = cocktailService.findCocktailByName(cocktailDTOWithId.getName());
//        if (!optionalCocktail.isPresent()) {
//            // Cocktail doesn't exist in C-Repo, return false
//            return false;
//        }

        // Step 1 : if User have this cocktail -> return false
        if (cocktailExistForUser(userResponseDTO,cocktailDTOWithId))
        {
            return false;
        }

        List<UCI> UCIList = new ArrayList<>();

        // Step 2:
        for (UCI_DTO.IngredientAmountDTO ingredientAmountDTO : ingredientAmountDTOList) {
            IngredientDTO ingredientDTO = ingredientAmountDTO.getIngredient();

            Optional<IngredientDTO> optionalIngredient = ingredientService.findIngredientByName(ingredientDTO.getName());

//            if (!optionalIngredient.isPresent()) {
//                // ingredient doesn't exist in I-Repo, return false
//                return false;
//            }

            // Step 3: Create a new CocktailIngredient entry
            UCI uci = new UCI();
            uci.setUser(userResponseDTO.to());
            uci.setCocktail(cocktailDTOWithId.to());
            uci.setIngredient(optionalIngredient.get().to());
            uci.setAmount(ingredientAmountDTO.getAmount());
            uci.setUnit(ingredientAmountDTO.getUnit());

            // add to List
            UCIList.add(uci);
        }


        // save to C-I only when it is true
        UCIRepository.saveAll(UCIList);

        return true;
    }

    public boolean deleteCocktailIngredient(UCI_DTO.Delete_UCI_DTO deleteUciDto)
    {
        // DTO
        UserDTO.UserResponseDTO userResponseDTO = deleteUciDto.getUserResponseDTO();
        CocktailDTO.CocktailDTOWithId cocktailDTOWithId = deleteUciDto.getCocktailDTOWithId();

        // ID
        Long userId = userResponseDTO.getUserId();
        Long cocktailId = cocktailDTOWithId.getCocktailId();

        // 1. see if this cocktail exist in C -repo
        if (cocktailService.findByID(cocktailId) == null)
        {
            // cocktail is not found by ID -> return false
            return false;
        }

        // 2. see if U-C exist in UC- repo
        if (!cocktailExistForUser(userResponseDTO, cocktailDTOWithId))
        {
            // Nothing to delete
            return false;
        }

        // 3. delete all UCI in UCI - repo
        UCIRepository.deleteByUserIdAndCocktailId(userId, cocktailId);
        return true;
    }

    // 유저가 가진 재료로 cocktail DTO List 반환
    public List<CocktailDTO.CocktailDTOWithId> findCocktailByUserIngredients(UCI_DTO.UCI_Ingredients_DTO ingredientsDTO)
    {
        // DTO
        UserDTO.UserResponseDTO userResponseDTO = ingredientsDTO.getUserResponseDTO();
        List<IngredientDTO> ingredients = ingredientsDTO.getIngredientDTO();

        // get cocktail List
        List<Cocktail> cocktailList = UCIRepository.findCocktailsByUserIngredients(userResponseDTO.getUserId());
        // get cocktail DTO list from List
        List<CocktailDTO.CocktailDTOWithId> cocktailDTOList = new java.util.ArrayList<>(List.of());
        for (Cocktail cocktail : cocktailList)
        {
            cocktailDTOList.add(CocktailDTO.CocktailDTOWithId.from(cocktail));
        }

        return cocktailDTOList;
    }

    // 유저가 선택한 재료로 cocktail DTO List 반환
    public List<CocktailDTO.CocktailDTOWithId> findCocktailBySelectedIngredients(UCI_DTO.UCI_Ingredients_DTO ingredientsDTO)
    {
        // DTO
        UserDTO.UserResponseDTO userResponseDTO = ingredientsDTO.getUserResponseDTO();
        List<IngredientDTO> ingredients = ingredientsDTO.getIngredientDTO();
        List<Long> ingredientsIds = new java.util.ArrayList<>(List.of());
        for (IngredientDTO ingredientDTO : ingredients)
        {
            ingredientsIds.add(ingredientDTO.getId());
        }

        List<Cocktail> cocktailList = UCIRepository.findCocktailsByIngredients(ingredientsIds);
        List<CocktailDTO.CocktailDTOWithId> cocktailDTOList = new java.util.ArrayList<>(List.of());
        for (Cocktail cocktail : cocktailList)
        {
            cocktailDTOList.add(CocktailDTO.CocktailDTOWithId.from(cocktail));
        }

        return cocktailDTOList;
    }
}
