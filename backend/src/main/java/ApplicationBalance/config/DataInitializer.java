package ApplicationBalance.config;

import ApplicationBalance.entities.Account;
import ApplicationBalance.entities.Expense;
import ApplicationBalance.entities.Role;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DataInitializer {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;


    @Value("${app.admin.password}")
    private String passwordadm;

    @Value("${app.admin.email}")
    private String emailadm;


    @PostConstruct
    public void automatecreateadmin() {

        if (!userRepository.existsByEmail(emailadm)) {
            String rolenameuser = "ROLE_USER";
            Role roleuser = new Role(rolenameuser);
            roleRepository.save(roleuser);
            String roleadminname = "ROLE_ADMIN";
            Role roleadmin = new Role(roleadminname);
            roleRepository.save(roleadmin);
            String name = "Nicholas Peterson Gonçalves Garcia";
            String email = emailadm;
            String passwordHash = passwordEncoder.encode(passwordadm);
            User user = new User(name, email, passwordHash, roleadmin);
            userRepository.save(user);
        }
        User user = userRepository.findByEmail(emailadm);

        if (user != null && accountRepository.findByUser(user) == null) {
            Account account = new Account();
            account.setBalance(BigDecimal.ZERO);
            account.setLastUpdated(LocalDateTime.now());
            account.setUser(user);
            accountRepository.save(account);
        }


    }

}
