package ApplicationBalance.repositories;

import ApplicationBalance.entities.Bill;
import ApplicationBalance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

  List<Bill> findByDueDate(LocalDate dueDate);
  Optional<Bill> findByIdAndUser(UUID id, User user);
  List<Bill> findAllByUser(User user);
}
