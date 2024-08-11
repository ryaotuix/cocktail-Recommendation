package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.User;
import cocktailrecommender.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cocktailrecommender.backend.utils.JwtCertificate;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private JwtCertificate jwtCertificate;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Sign Up
    public boolean createUser(UserDTO.UserRequestDTO userRequestDTO){
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            return false;
        }
        userRepository.save(userRequestDTO.toUser());
        return true;
    }
    //Delete
    public boolean deleteUser(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            userRepository.deleteById(userOptional.get().getUserId());
            return true;
        }
        return false;
    }
    public boolean login(UserDTO.UserRequestDTO userRequestDTO){
        Optional<User> userOptional = userRepository.findByEmail(userRequestDTO.getEmail());
        return userOptional.isPresent() && userOptional.get().getPassword().equals(userRequestDTO.getPassword());
    }

    //Modify User Password, needed to be encrypted
    public boolean changePassword(Long userId, String password){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public boolean changeName(Long userId, String name){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
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

    public UserDTO.UserResponseDTO findByID(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserDTO.UserResponseDTO::new).orElse(null);
    }

    // email이 없으면 null 아니면 userDTO반환
    public UserDTO.UserResponseDTO getUserFromToken(String token) {
        String userEmail = jwtCertificate.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(userEmail);
        return user.map(UserDTO.UserResponseDTO::new).orElse(null);
    }
}
