package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;
    private String name;

    @OneToMany(mappedBy = "ingredient")
    private Set<CocktailIngredient> cocktailIngredients;

    @OneToMany(mappedBy = "ingredient")
    private Set<UserIngredient> userIngredients;

    @OneToMany(mappedBy = "ingredient")
    private Set<IngredientTaste> ingredientTastes;

    public Ingredient(String name){
        this.name = name;
    }
    public Ingredient(Long id, String name){
        this.ingredientId = id;
        this.name = name;
    }
}