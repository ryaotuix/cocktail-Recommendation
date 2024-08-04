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
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cocktailId;
    private String name;
    private String howToMake;       //

    @OneToMany(mappedBy = "cocktail")
    private Set<CocktailIngredient> cocktailIngredients;

    @OneToMany(mappedBy = "cocktail")
    private Set<CocktailTaste> cocktailTastes;

    // getters and setters
}