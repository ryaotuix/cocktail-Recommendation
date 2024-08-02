package cocktailrecommender.backend.domain;
import jakarta.persistence.*;

@Entity
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