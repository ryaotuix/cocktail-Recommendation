package cocktailrecommender.backend.repository;
import cocktailrecommender.backend.domain.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CocktailRepository extends JpaRepository<Cocktail, Long>{
    Optional<Cocktail> findByName(String name);
    Optional<Cocktail> findByID(long id);
    List<Cocktail> findByNameContainingIgnoreCase(String name);
    List<Cocktail> findAll();
}