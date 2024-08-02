package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.CocktailTaste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailTasteRepository extends JpaRepository<CocktailTaste, Long> {
    // 필요한 추가 쿼리 메서드를 정의할 수 있습니다.
}