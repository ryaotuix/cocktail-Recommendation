package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.IngredientTaste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientTasteRepository extends JpaRepository<IngredientTaste, Long> {

}