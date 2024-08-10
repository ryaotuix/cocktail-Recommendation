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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRequestDTO {
        private String email;
        private String name;
        private String password;

        public User toUser() {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            return user;
        }

        public static UserRequestDTO from(User user) {
            return new UserRequestDTO(user.getEmail(), user.getName(), user.getPassword());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponseDTO {
        private Long userId;
        private String email;
        private String name;
        private String password;

        public UserResponseDTO(User user) {
            this.userId = user.getUserId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.password = user.getPassword();
        }
        public User to(){
            User user = new User();
            user.setUserId(this.userId);
            user.setName(this.name);
            user.setEmail(this.email);
            user.setPassword(this.password);
            return user;
        }
    }
}
