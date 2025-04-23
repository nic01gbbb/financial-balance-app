package ApplicationBalance.config;


import ApplicationBalance.dtos.user.UserCreateDTO;
import ApplicationBalance.dtos.user.UserDTO;
import ApplicationBalance.repositories.UserRepository;
import ApplicationBalance.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody config.LoginRequest request) {
        return authService.login(request);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserCreateDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(userService.registerUser(dto));
    }


}
