package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.UserCocktailIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCocktailIngredientRepository extends JpaRepository<UserCocktailIngredient, Long> {
    @Query("SELECT c FROM Cocktail c WHERE NOT EXISTS (" +
            "SELECT 1 FROM UserCocktailIngredient uci " +
            "WHERE uci.cocktail = c " +
            "AND uci.user.userId IN (1, :userId) " +
            "AND uci.ingredient.ingredientId NOT IN :ingredientIds)")
    List<Cocktail> findCocktailsByIngredientsAndUser(@Param("ingredientIds") List<Long> ingredientIds, @Param("userId") Long userId);
}