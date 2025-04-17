package ApplicationBalance.repositories;

import ApplicationBalance.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {

boolean existsByName(String name);

}
