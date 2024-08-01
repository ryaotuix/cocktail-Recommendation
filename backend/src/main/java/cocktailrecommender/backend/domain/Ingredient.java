package cocktailrecommender.backend.domain;

public class Ingredient {
    private int ingredient_id;
    private String ingredient_name;

    public Ingredient(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }
}
