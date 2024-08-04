package cocktailrecommender.backend;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.User;
import cocktailrecommender.backend.repository.UserRepository;
import cocktailrecommender.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void signUp() {
        // Arrange
        User user1 = new User();
        User user2 = new User();

        user1.setName("name1");
        user2.setName("name2");

        user1.setEmail("email1");
        user2.setEmail("email1");

        user1.setPassword("pw1");
        user2.setPassword("pw2");

        assertTrue(userService.createUser(UserDTO.UserRequestDTO.from(user1)));
        assertFalse(userService.createUser(UserDTO.UserRequestDTO.from(user2)));
    }
    @Test
    void delete(){
        List<User> users = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setName("name1");
        user2.setName("name2");
        user3.setName("name3");

        user1.setEmail("email1");
        user2.setEmail("email2");
        user3.setEmail("email3");

        user1.setPassword("pw1");
        user2.setPassword("pw2");
        user3.setPassword("pw3");

        users.add(user1); users.add(user2);

        userService.createUser(UserDTO.UserRequestDTO.from(user1));
        userService.createUser(UserDTO.UserRequestDTO.from(user2));
        userService.createUser(UserDTO.UserRequestDTO.from(user3));

        assertTrue(userService.deleteUser(3L));
        List<User> remainUsers = userRepository.findAll();
        assertEquals(2,remainUsers.size());
        assertEquals(user1.getEmail(),remainUsers.get(0).getEmail());
        assertEquals(user2.getEmail(),remainUsers.get(1).getEmail());
    }
}