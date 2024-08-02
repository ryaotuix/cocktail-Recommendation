package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Taste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tasteId;

    private String name;

    @OneToMany(mappedBy = "taste")
    private Set<CocktailTaste> cocktailTastes;

    @OneToMany(mappedBy = "taste")
    private Set<IngredientTaste> ingredientTastes;

    // getters and setters
}
