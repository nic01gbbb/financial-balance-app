package ApplicationBalance.repositories;

import ApplicationBalance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


    boolean existsByEmail(String email);

    User findByname(String name);

    User findByEmail(String email);


    Optional<User> findByName(String name);


}
