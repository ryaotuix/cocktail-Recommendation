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
        private String ingredientName;
        private Double amount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUCIDTO {
        private Long userId;
        private CocktailDTO.CocktailDTOWithoutId cocktailDTOWithoutId;
        private List<IngredientAmountDTO> ingredientAmountDTOList;
    }
}
