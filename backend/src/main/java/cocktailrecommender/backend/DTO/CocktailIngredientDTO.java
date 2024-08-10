package cocktailrecommender.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CocktailIngredientDTO {
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
    public static class CreateCIDTO{
        private CocktailDTO.CocktailDTOWithId cocktailDTOWithId;
        private List<IngredientAmountDTO> ingredientAmountDTOList;
    }
}
