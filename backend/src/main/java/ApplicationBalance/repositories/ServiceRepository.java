package ApplicationBalance.repositories;

import ApplicationBalance.entities.Service;
import ApplicationBalance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {

    boolean existsByName(String name);

    Service findByName(String name);

    List<Service> findAllByUser(User user);

}
