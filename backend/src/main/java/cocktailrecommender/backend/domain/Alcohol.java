package cocktailrecommender.backend.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Alcohol {
    @Id
    private int alcohol_id;
    private String alcohol_name;

    public Alcohol(int alcohol_id, String alcohol_name) {
        this.alcohol_id = alcohol_id;
        this.alcohol_name = alcohol_name;
    }

    public int getAlcohol_id() {
        return alcohol_id;
    }

    public void setAlcohol_id(int alcohol_id) {
        this.alcohol_id = alcohol_id;
    }

    public String getAlcohol_name() {
        return alcohol_name;
    }

    public void setAlcohol_name(String alcohol_name) {
        this.alcohol_name = alcohol_name;
    }
}
