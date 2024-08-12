package cocktailrecommender.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class UCI_DTO {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class IngredientAmountDTO{
        private IngredientDTO ingredient;
        private Double amount;
        private String unit;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create_UCI_DTO {
        private UserDTO.UserResponseDTO userResponseDTO;
        private CocktailDTO.CocktailDTOWithId cocktailDTOWithId;
        private List<IngredientAmountDTO> ingredientAmountDTOList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Delete_UCI_DTO {
        private UserDTO.UserResponseDTO userResponseDTO;
        private CocktailDTO.CocktailDTOWithId cocktailDTOWithId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UCI_Ingredients_DTO {
        private UserDTO.UserResponseDTO userResponseDTO;
        private List<IngredientDTO> IngredientDTO;
    }

}
