package ApplicationBalance.repositories;

import ApplicationBalance.entities.Profit;
import ApplicationBalance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProfitRepository extends JpaRepository<Profit, UUID> {
    boolean existsBy();

    Profit findByUser(User user);


}
