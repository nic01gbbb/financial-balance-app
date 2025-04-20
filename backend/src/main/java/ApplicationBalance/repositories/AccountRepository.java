package ApplicationBalance.repositories;

import ApplicationBalance.entities.Account;
import ApplicationBalance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account findByUser(User user);

    Optional<Account> findByUserId(UUID userId);


}
