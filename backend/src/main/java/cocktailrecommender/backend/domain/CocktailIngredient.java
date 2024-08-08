package cocktailrecommender.backend.domain;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
public class CocktailIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cocktailId")
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    private Double amount;

    // getters and setters
}