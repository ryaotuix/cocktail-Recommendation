package cocktailrecommender.backend.repository;

import cocktailrecommender.backend.domain.UserCocktail;
import cocktailrecommender.backend.domain.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCocktailRepository extends JpaRepository<UserCocktail, Long> {
}
