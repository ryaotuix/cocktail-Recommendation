package cocktailrecommender.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cocktail {
    @Id
    @GeneratedValue
    private Integer cocktail_id;
    private String cocktail_name;

    // Constructor
    public Cocktail(String cocktail_name) {
        this.cocktail_name = cocktail_name;
    }

    public String getCocktail_name() {
        return cocktail_name;
    }

    public void setCocktail_name(String cocktail_name) {
        this.cocktail_name = cocktail_name;
    }

    public int getCocktail_id() {
        return cocktail_id;
    }

    public void setCocktail_id(int cocktail_id) {
        this.cocktail_id = cocktail_id;
    }
}
