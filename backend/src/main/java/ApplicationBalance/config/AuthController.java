package ApplicationBalance.config;

import ApplicationBalance.dtos.user.UserCreateDTO;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.RoleRepository;
import ApplicationBalance.repositories.UserRepository;
import ApplicationBalance.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserCreateDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        userService.registerUser(user); // ou jwtService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errormsg = bindingResult.getFieldErrors().getFirst().getDefaultMessage();

            return ResponseEntity
                    .badRequest()
                    .body(new LoginResponse(null, errormsg));
        }

        return authService.login(request);
    }

}
