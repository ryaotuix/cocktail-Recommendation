package cocktailrecommender.backend.repository;

import cocktailrecommender.backend.domain.Cocktail;

import java.util.Map;

public class MemoryCocktailRepository {
    private static Map<Integer, Cocktail> cocktails;
    private static int cocktail_id = 0;

    // methods
    public void createCocktail(String name)
    {
        Cocktail cocktail = new Cocktail(name);
        cocktail.setCocktail_id(cocktail_id);
        cocktail_id++;
    }


}
