package cocktailrecommender.backend.domain;

public class Cocktail {
    static private int cocktail_id;
    private String cocktail_name;

    public Cocktail(String cocktail_name) {
        this.cocktail_name = cocktail_name;
    }

    public String getCocktail_name() {
        return cocktail_name;
    }

    public void setCocktail_name(String cocktail_name) {
        this.cocktail_name = cocktail_name;
    }

    public static int getCocktail_id() {
        return cocktail_id;
    }

    public static void setCocktail_id(int cocktail_id) {
        Cocktail.cocktail_id = cocktail_id;
    }
}
