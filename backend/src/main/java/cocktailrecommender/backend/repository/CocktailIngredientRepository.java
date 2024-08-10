package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.CocktailIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {

    @Query(value = "SELECT c.* FROM Cocktail c WHERE NOT EXISTS (" +
            "SELECT 1 FROM CocktailIngredient ci " +
            "WHERE ci.cocktail_id = c.id " +
            "AND ci.ingredient_id NOT IN :ingredientIds " +
            "GROUP BY ci.cocktail_id " +
            "HAVING COUNT(DISTINCT ci.ingredient_id) < :ingredientCount)",
            nativeQuery = true)
    List<Cocktail> findByIngredients(@Param("ingredientIds") List<Long> ingredientIds,
                                     @Param("ingredientCount") int ingredientCount);
}