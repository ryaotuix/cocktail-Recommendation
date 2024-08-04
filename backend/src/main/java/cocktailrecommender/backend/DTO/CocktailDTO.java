package cocktailrecommender.backend.DTO;

import java.util.Set;

public class CocktailDTO {

    private Long cocktailId;
    private String name;
    private String howToMake;
    private Set<CocktailIngredientDTO> cocktailIngredients;
    private Set<CocktailTasteDTO> cocktailTastes;

    // Constructors
    public CocktailDTO() {
    }

    public CocktailDTO(Long cocktailId, String name, String howToMake, Set<CocktailIngredientDTO> cocktailIngredients, Set<CocktailTasteDTO> cocktailTastes) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.howToMake = howToMake;
        this.cocktailIngredients = cocktailIngredients;
        this.cocktailTastes = cocktailTastes;
    }

    // Getters and setters
    public Long getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(Long cocktailId) {
        this.cocktailId = cocktailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHowToMake() {
        return howToMake;
    }

    public void setHowToMake(String howToMake) {
        this.howToMake = howToMake;
    }

    public Set<CocktailIngredientDTO> getCocktailIngredients() {
        return cocktailIngredients;
    }

    public void setCocktailIngredients(Set<CocktailIngredientDTO> cocktailIngredients) {
        this.cocktailIngredients = cocktailIngredients;
    }

    public Set<CocktailTasteDTO> getCocktailTastes() {
        return cocktailTastes;
    }

    public void setCocktailTastes(Set<CocktailTasteDTO> cocktailTastes) {
        this.cocktailTastes = cocktailTastes;
    }
}
