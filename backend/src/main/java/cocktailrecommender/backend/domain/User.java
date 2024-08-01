package cocktailrecommender.backend.domain;

import java.util.Map;

public class User {
    private String username;
    private String password;
    private Integer user_id;
    private Map<Taste, Integer> tastePreference;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Map<Taste, Integer> getTastePreference() {
        return tastePreference;
    }

    public void setTastePreference(Map<Taste, Integer> tastePreference) {
        this.tastePreference = tastePreference;
    }
}
