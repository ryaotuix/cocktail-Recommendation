package cocktailrecommender.backend.controller;

import cocktailrecommender.backend.DTO.UserDTO;
import cocktailrecommender.backend.service.UserService;
import cocktailrecommender.backend.utils.JwtCertificate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody UserDTO.UserRequestDTO userRequestDTO){

        if(userService.createUser(userRequestDTO)){
            return ResponseEntity.ok("Register Succeeded");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Register Failed");
    }
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO.UserRequestDTO userRequestDTO){
        JSONObject responseJson = new JSONObject();

        //when login success
        if(userService.login(userRequestDTO)){
            JwtCertificate jwtCertificate = new JwtCertificate();
            String token = jwtCertificate.generateToken(userRequestDTO.getEmail());
            responseJson.put("token",token);
            return ResponseEntity.ok(responseJson.toString());
        }else{
            responseJson.put("message", "login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseJson.toString());
        }
    }
}
