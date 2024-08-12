package cocktailrecommender.backend.domain;
import cocktailrecommender.backend.DTO.CocktailDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cocktailId;
    private String name;
    private String howToMake;       //

    @OneToMany(mappedBy = "cocktail")
    private Set<UCI> UCIs;

    @OneToMany(mappedBy = "cocktail")
    private Set<CocktailTaste> cocktailTastes;

    @OneToMany(mappedBy = "cocktail")
    private Set<UCI> userCocktails;


    public CocktailDTO.CocktailDTOWithoutId toWithoutId() {
        return new CocktailDTO.CocktailDTOWithoutId(this.name, this.howToMake);
    }
}