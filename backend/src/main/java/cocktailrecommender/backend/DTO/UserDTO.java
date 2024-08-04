package cocktailrecommender.backend.DTO;

import cocktailrecommender.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserRequestDTO{
        private String email;
        private String name;
        private String password;
        public User toUser(){
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            return user;
        }
        public static UserRequestDTO from(User user){
            return new UserRequestDTO(user.getEmail(), user.getEmail(), user.getPassword());
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponseDTO{
        private Long userId;
        private String email;
        private String name;
        private String password;
    }
}
