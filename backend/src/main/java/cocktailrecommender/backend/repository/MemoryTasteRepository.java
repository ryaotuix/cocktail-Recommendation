package cocktailrecommender.backend.repository;

import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.Taste;

import java.util.HashMap;
import java.util.Map;

public class MemoryTasteRepository {
    private static Map<Integer, Taste> tastes = new HashMap<>();
    private static int taste_id = 0;

    // methods
    public void createTaste(String name)
    {
        Taste t = new Taste(name);
        tastes.put(taste_id, t);
        taste_id++;
    }

}
