package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {
    // 필요한 추가 쿼리 메서드를 정의할 수 있습니다.
}