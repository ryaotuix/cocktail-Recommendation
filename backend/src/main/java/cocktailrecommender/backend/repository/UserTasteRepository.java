package cocktailrecommender.backend.repository;

import cocktailrecommender.backend.domain.UserTaste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTasteRepository extends JpaRepository<UserTaste, Long> {
}
