package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.UCI_DTO;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.UCI;
import cocktailrecommender.backend.repository.UCIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UCIService {
    private final UCIRepository UCIRepository;
    private final CocktailService cocktailService;
    private final IngredientService ingredientService;
    private final UserCocktailService userCocktailService;

    @Autowired
    public UCIService(UCIRepository UCIRepository, CocktailService cocktailService, IngredientService ingredientService, UserCocktailService userCocktailService) {
        this.UCIRepository = UCIRepository;
        this.cocktailService = cocktailService;
        this.ingredientService = ingredientService;
        this.userCocktailService = userCocktailService;
    }


    // 칵테일 생성
    // Return false if the cocktail already exists or if any ingredient does not exist
    public boolean createCocktailWithIngredient(UCI_DTO.Create_UCI_DTO createUCI_DTO) {

        UserDTO.UserResponseDTO userResponseDTO = createUCI_DTO.getUserResponseDTO();
        CocktailDTO.CocktailDTOWithId cocktailDTOWithId = createUCI_DTO.getCocktailDTOWithId();
        List<UCI_DTO.IngredientAmountDTO> ingredientAmountDTOList = createUCI_DTO.getIngredientAmountDTOList();

        // Step 1:
        Optional<CocktailDTO.CocktailDTOWithId> optionalCocktail = cocktailService.findCocktailByName(cocktailDTOWithId.getName());
        if (!optionalCocktail.isPresent()) {
            // Cocktail doesn't exist in C-Repo, return false
            return false;
        }

        List<UCI> UCIList = List.of();

        // Step 2:
        for (UCI_DTO.IngredientAmountDTO ingredientAmountDTO : ingredientAmountDTOList) {
            IngredientDTO ingredientDTO = ingredientAmountDTO.getIngredient();

            Optional<IngredientDTO> optionalIngredient = ingredientService.findIngredientByName(ingredientDTO.getName());

            if (!optionalIngredient.isPresent()) {
                // ingredient doesn't exist in I-Repo, return false
                return false;
            }

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

        // save U-C if not exist
        if (!userCocktailService.cocktailExistForUser(userResponseDTO, cocktailDTOWithId))
        {
            userCocktailService.createUserCocktail(userResponseDTO, cocktailDTOWithId);
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
        if (!userCocktailService.cocktailExistForUser(userResponseDTO, cocktailDTOWithId))
        {
            // Nothing to delete
            return false;
        }

        // 3. delete all UCI in UCI - repo
        UCIRepository.deleteByUserIdAndCocktailId(userId, cocktailId);
        return true;
    }

}
