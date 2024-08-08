package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.IngredientDTO;
import cocktailrecommender.backend.domain.Ingredient;
import cocktailrecommender.backend.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public boolean createIngredient(String name){
        if(ingredientRepository.findByName(name).isPresent()){
            return false;
        }
        ingredientRepository.save(new Ingredient(name));
        return true;
    }
    //if not exists: return 0
    public Optional<IngredientDTO> findIngredientByName(String name){
        return ingredientRepository.findByName(name).map(
                IngredientDTO::from
        );
    }
}
