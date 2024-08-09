package cocktailrecommender.backend.DTO;

import cocktailrecommender.backend.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IngredientDTO {
    private Long id;
    private String name;
    public Ingredient to(){
        return new Ingredient(this.id, this.name);
    }
    public static IngredientDTO from(Ingredient ingredient){
        return new IngredientDTO(ingredient.getIngredientId(), ingredient.getName());
    }
}
