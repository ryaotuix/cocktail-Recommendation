package cocktailrecommender.backend.service;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.domain.User;
import cocktailrecommender.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private final UserRepository userRepository;

    //Sign Up
    public boolean createUser(UserDTO userDTO){
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            return false;
        }
        userRepository.save(userDTO.toUser());
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
    public boolean login(UserDTO userDTO){
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        return userOptional.isPresent() && userOptional.get().getPassword().equals(userDTO.getPassword());
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
}
