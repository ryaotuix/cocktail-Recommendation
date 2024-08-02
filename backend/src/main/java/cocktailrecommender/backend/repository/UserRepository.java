package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 필요한 추가 쿼리 메서드를 정의할 수 있습니다.
}
