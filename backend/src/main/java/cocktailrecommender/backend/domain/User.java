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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;


    @OneToMany(mappedBy = "user")
    private Set<UserIngredient> userIngredients;

    @OneToMany(mappedBy = "user")
    private Set<UserTaste> userTastes;
}
