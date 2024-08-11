package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
public class UCI {
    @Getter
    @Setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cocktailId")
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Double amount;
    private String unit;


}