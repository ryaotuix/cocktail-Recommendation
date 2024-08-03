package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // 필요한 추가 쿼리 메서드를 정의할 수 있습니다.
}
