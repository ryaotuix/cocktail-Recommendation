package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.CocktailIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {
    @Query("SELECT c FROM Cocktail c WHERE NOT EXISTS (" +
            "SELECT 1 FROM CocktailIngredient ci " +
            "WHERE ci.cocktail = c " +
            "AND ci.ingredient.ingredientId NOT IN :ingredientIds)")
    List<Cocktail> findCocktailsByIngredients(@Param("ingredientIds") List<Long> ingredientIds);
}