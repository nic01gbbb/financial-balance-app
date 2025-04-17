package ApplicationBalance.repositories;

import ApplicationBalance.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsBy();

    Optional<Account> findByUserId(UUID userId);


}
