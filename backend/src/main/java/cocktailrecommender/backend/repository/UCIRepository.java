package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.UCI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UCIRepository extends JpaRepository<UCI, Long> {
    @Query("SELECT c FROM Cocktail c WHERE NOT EXISTS (" +
            "SELECT 1 FROM UCI ci " +
            "WHERE ci.cocktail = c " +
            "AND ci.ingredient.ingredientId NOT IN :ingredientIds)")
    List<Cocktail> findCocktailsByIngredients(@Param("ingredientIds") List<Long> ingredientIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM UCI u WHERE u.user.userId = :userId AND u.cocktail.cocktailId = :cocktailId")
    void deleteByUserIdAndCocktailId(@Param("userId") Long userId, @Param("cocktailId") Long cocktailId);
}