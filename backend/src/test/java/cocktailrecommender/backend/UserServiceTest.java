package cocktailrecommender.backend;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.User;
import cocktailrecommender.backend.repository.UserRepository;
import cocktailrecommender.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        UserDTO.UserRequestDTO userRequestDTO = new UserDTO.UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("password123");
        userRequestDTO.setName("John Doe");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = userService.createUser(userRequestDTO);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        UserDTO.UserRequestDTO userRequestDTO = new UserDTO.UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("password123");
        userRequestDTO.setName("John Doe");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        boolean result = userService.createUser(userRequestDTO);

        assertFalse(result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.deleteUser(1L);

        assertFalse(result);
        verify(userRepository, times(0)).deleteById(1L);
    }

    @Test
    void testLogin_Success() {
        UserDTO.UserRequestDTO userRequestDTO = new UserDTO.UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("password123");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.login(userRequestDTO);

        assertTrue(result);
    }

    @Test
    void testLogin_WrongPassword() {
        UserDTO.UserRequestDTO userRequestDTO = new UserDTO.UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.login(userRequestDTO);

        assertFalse(result);
    }

    @Test
    void testLogin_UserNotFound() {
        UserDTO.UserRequestDTO userRequestDTO = new UserDTO.UserRequestDTO();
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        boolean result = userService.login(userRequestDTO);

        assertFalse(result);
    }

    @Test
    void testChangePassword_Success() {
        User user = new User();
        user.setUserId(1L);
        user.setPassword("oldpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.changePassword(1L, "newpassword");

        assertTrue(result);
        assertEquals("newpassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangePassword_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.changePassword(1L, "newpassword");

        assertFalse(result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testChangeName_Success() {
        User user = new User();
        user.setUserId(1L);
        user.setName("Old Name");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.changeName(1L, "New Name");

        assertTrue(result);
        assertEquals("New Name", user.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangeName_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.changeName(1L, "New Name");

        assertFalse(result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testFindByEmail_UserExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("John Doe");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDTO.UserResponseDTO result = userService.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testFindByEmail_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        UserDTO.UserResponseDTO result = userService.findByEmail("test@example.com");

        assertNull(result);
    }
}
