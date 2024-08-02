package cocktailrecommender.backend.domain;
import jakarta.persistence.*;

@Entity
public class IngredientTaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "tasteId")
    private Taste taste;

    // getters and setters
}
