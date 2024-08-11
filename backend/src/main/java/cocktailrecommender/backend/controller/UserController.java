package cocktailrecommender.backend.controller;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.service.UserService;
import cocktailrecommender.backend.utils.JwtCertificate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtCertificate jwtCertificate;

    @Autowired
    public UserController(UserService userService, JwtCertificate jwtCertificate) {
        this.userService = userService;
        this.jwtCertificate = jwtCertificate;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody UserDTO.UserRequestDTO userRequestDTO) {
        if (userService.createUser(userRequestDTO)) {
            return ResponseEntity.ok("Register Succeeded");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Register Failed");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO.UserRequestDTO userRequestDTO) {
        JSONObject responseJson = new JSONObject();
        if (userService.login(userRequestDTO)) {
            String token = jwtCertificate.generateToken(userRequestDTO.getEmail());
            responseJson.put("token", token);
            return ResponseEntity.ok(responseJson.toString());
        } else {
            responseJson.put("message", "login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseJson.toString());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId, @RequestHeader("Authorization") String token) {
        // 토큰에서 "Bearer " 접두사를 제거하고 실제 토큰 값만 추출
        String actualToken = token.replace("Bearer ", "");

        // 토큰에서 이메일 추출
        String email = jwtCertificate.extractEmail(actualToken);

        // 이메일을 통해 사용자 정보 확인
        UserDTO.UserResponseDTO userResponseDTO = userService.findByEmail(email);

        // 사용자 ID가 일치하는지 확인
        if (userResponseDTO != null && userResponseDTO.getUserId().equals(userId)) {
            if (userService.deleteUser(userId)) {
                return ResponseEntity.ok("User deleted successfully");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to delete user");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam Long userId, @RequestParam String password) {
        if (userService.changePassword(userId, password)) {
            return ResponseEntity.ok("Password changed successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/changeName")
    public ResponseEntity<String> changeName(@RequestParam Long userId, @RequestParam String name) {
        if (userService.changeName(userId, name)) {
            return ResponseEntity.ok("Name changed successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    // change taste preference
    // change ingredient list

}
