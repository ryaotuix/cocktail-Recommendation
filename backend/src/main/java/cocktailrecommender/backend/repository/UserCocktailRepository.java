package cocktailrecommender.backend.repository;

import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.domain.UserCocktail;
import cocktailrecommender.backend.domain.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCocktailRepository extends JpaRepository<UserCocktail, Long> {
    @Query("SELECT uc.cocktail FROM UserCocktail uc WHERE uc.user.userId = :userId")
    List<Cocktail> findCocktailsByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndCocktailId(Long u_id, Long c_id);
}
