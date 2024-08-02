package cocktailrecommender.backend.domain;
import jakarta.persistence.*;

@Entity
public class UserIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    // getters and setters
}
