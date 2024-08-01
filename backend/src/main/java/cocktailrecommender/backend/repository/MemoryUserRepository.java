package cocktailrecommender.backend.repository;

import cocktailrecommender.backend.domain.User;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository {
    private static Map<Integer, User> users = new HashMap<>();
    private static int user_id = 0;

    // methods
    public void createUser(String username, String password)
    {
        User user = new User(username, password);
        users.put(user_id, user);
        user_id++;
    }

}
