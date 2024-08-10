package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.CocktailDTO;
import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.UserCocktail;
import cocktailrecommender.backend.repository.UserCocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCocktailService {
    private final UserCocktailRepository userCocktailRepository;
    @Autowired
    public UserCocktailService(UserCocktailRepository userCocktailRepository) {
        this.userCocktailRepository = userCocktailRepository;
    }

    public void createUserCocktail(UserDTO.UserResponseDTO userResponseDTO, CocktailDTO.CocktailDTOWithId cocktailDTOWithId){
        UserCocktail userCocktail = new UserCocktail();
        userCocktail.setUser(userResponseDTO.to());
        userCocktail.setCocktail(cocktailDTOWithId.to());
        userCocktailRepository.save(userCocktail);
    }
}
