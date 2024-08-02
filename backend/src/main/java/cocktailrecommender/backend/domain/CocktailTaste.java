package cocktailrecommender.backend.domain;
import jakarta.persistence.*;

@Entity
public class CocktailTaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cocktailId")
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name = "tasteId")
    private Taste taste;

    // getters and setters
}
