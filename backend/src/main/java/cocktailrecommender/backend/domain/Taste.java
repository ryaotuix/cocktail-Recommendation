package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Taste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tasteId;
    private String name;
    private int level;

    @OneToMany(mappedBy = "taste")
    private Set<CocktailTaste> cocktailTastes;

    @OneToMany(mappedBy = "taste")
    private Set<IngredientTaste> ingredientTastes;

    @OneToMany(mappedBy = "taste")
    private Set<UserTaste> userTastes;
    // getters and setters
}
