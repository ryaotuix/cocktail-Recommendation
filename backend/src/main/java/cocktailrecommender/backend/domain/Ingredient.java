package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import java.util.Set;

@Entity
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

    // getters and setters
}