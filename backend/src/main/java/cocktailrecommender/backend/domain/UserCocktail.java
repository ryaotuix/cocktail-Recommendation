package cocktailrecommender.backend.domain;

import jakarta.persistence.*;

@Entity
public class UserCocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cocktailId")
    private Cocktail cocktail;
}