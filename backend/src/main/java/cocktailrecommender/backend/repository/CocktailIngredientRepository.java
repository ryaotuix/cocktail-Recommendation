package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.CocktailIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {
    // 필요한 추가 쿼리 메서드를 정의할 수 있습니다.
}