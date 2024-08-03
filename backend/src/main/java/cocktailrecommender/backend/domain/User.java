package cocktailrecommender.backend.domain;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @OneToMany(mappedBy = "user")
    private Set<UserIngredient> userIngredients;

    // getters and setters

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserIngredients(Set<UserIngredient> userIngredients) {
        this.userIngredients = userIngredients;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Set<UserIngredient> getUserIngredients() {
        return userIngredients;
    }
}
