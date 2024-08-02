package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    // 필요한 추가 쿼리 메서드를 정의할 수 있습니다.
}