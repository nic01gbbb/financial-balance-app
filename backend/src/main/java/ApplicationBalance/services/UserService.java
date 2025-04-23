package ApplicationBalance.services;
import ApplicationBalance.dtos.user.UserCreateDTO;
import ApplicationBalance.dtos.user.UserDTO;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.RoleRepository;
import ApplicationBalance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Validated
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    public UserDTO registerUser(UserCreateDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está em uso");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(newUser);
        return convertToUserDTO(newUser);
    }


    // Helper method to convert Users entity to UserDTO
    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Map entity list to DTO list
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserDTO(foundUser.getId(), foundUser.getName(), foundUser.getEmail(), foundUser.getRole());
        }
        return null; // Return null if the user isn't found (controller handles this)
    }


}

