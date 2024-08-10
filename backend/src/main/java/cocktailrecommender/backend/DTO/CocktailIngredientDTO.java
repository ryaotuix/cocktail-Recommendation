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
        private CocktailDTO.CocktailDTOWithoutId cocktailDTOWithoutId;
        private List<IngredientAmountDTO> ingredientAmountDTOList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCIDTO {
        private Long cocktailId; // 수정할 칵테일의 ID
        private String ingredientName; // 수정할 재료의 이름
        private Double newAmount; // 새로 설정할 양
    }
}
