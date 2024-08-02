package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String password;
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<UserIngredient> userIngredients;

    // getters and setters
}
