package cocktailrecommender.backend.service;

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
}
