package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.User;
import cocktailrecommender.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(UserDTO.UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            return false;
        }
        userRepository.save(userRequestDTO.toUser());
        return true;
    }

    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userOptional.get().getUserId());
            return true;
        }
        return false;
    }

    public boolean login(UserDTO.UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userRequestDTO.getEmail());
        return userOptional.isPresent() && userOptional.get().getPassword().equals(userRequestDTO.getPassword());
    }

    public boolean changePassword(Long userId, String password) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean changeName(Long userId, String name) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(name);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UserDTO.UserResponseDTO findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(UserDTO.UserResponseDTO::new).orElse(null);
    }
}
