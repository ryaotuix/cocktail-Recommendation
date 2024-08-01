package cocktailrecommender.backend.domain;

public class Taste {
    private int taste_id;
    private String taste_name;

    public Taste(String taste_name) {
        this.taste_name = taste_name;
    }

    public int getTaste_id() {
        return taste_id;
    }

    public void setTaste_id(int taste_id) {
        this.taste_id = taste_id;
    }

    public String getTaste_name() {
        return taste_name;
    }

    public void setTaste_name(String taste_name) {
        this.taste_name = taste_name;
    }
}
