package cocktailrecommender.backend.DTO;

import cocktailrecommender.backend.domain.Cocktail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

public class CocktailDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CocktailDTOWithoutId{
        private String name;
        private String howToMake;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CocktailDTOWithId{
        private Long cocktailId;
        private String name;
        private String howToMake;
        public static CocktailDTOWithId from(Cocktail cocktail){
            return new CocktailDTOWithId(
                    cocktail.getCocktailId(),cocktail.getName(), cocktail.getHowToMake()
            );
        }
        public Cocktail to(){
            Cocktail cocktail = new Cocktail();
            cocktail.setCocktailId(this.cocktailId);
            cocktail.setName(this.name);
            cocktail.setHowToMake(this.howToMake);
            return cocktail;
        }
    }
}
