package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.domain.Cocktail;
import cocktailrecommender.backend.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CocktailService {
    @Autowired
    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public boolean createCocktail(CocktailDTO.CocktailDTOWithoutId cocktailDTOWithoutId){
        //if same name exists: cannot create
        if(cocktailRepository.findByNameIgnoreCase(cocktailDTOWithoutId.getName()).isPresent()){
            return false;
        }else{
            Cocktail cocktail = new Cocktail();
            cocktail.setName(cocktailDTOWithoutId.getName());
            cocktail.setHowToMake(cocktailDTOWithoutId.getHowToMake());
            cocktailRepository.save(cocktail);
            return true;
        }
    }
    public Optional<CocktailDTO.CocktailDTOWithId> findCocktailByName(String name){
        return cocktailRepository.findByNameIgnoreCase(name).map(
                cocktail -> CocktailDTO.CocktailDTOWithId.from(cocktail)
        );
    }
    //find cocktails by name
    public List<CocktailDTO.CocktailDTOWithId> findCocktailsByName(String name){
        return cocktailRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CocktailDTO.CocktailDTOWithId::from)
                .collect(Collectors.toList());
    }

    //Edit howToMake
    public boolean editHowToMake(Long cocktailId, String newHowToMake){
        return cocktailRepository.findById(cocktailId)
                .map(cocktail -> {
                    cocktail.setHowToMake(newHowToMake);
                    cocktailRepository.save(cocktail);
                    return true;
                })
                .orElse(false);
    }

    //Delete Cocktail
    public boolean deleteCocktail(Long cocktailId){
        Optional<Cocktail> cocktail = cocktailRepository.findById(cocktailId);
        if (cocktail.isPresent()) {
            cocktailRepository.deleteById(cocktailId);
            return true;
        } else {
            return false;
        }
    }
}
