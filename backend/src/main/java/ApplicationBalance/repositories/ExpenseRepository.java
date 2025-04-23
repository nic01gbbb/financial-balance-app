package ApplicationBalance.repositories;

import ApplicationBalance.entities.Expense;
import ApplicationBalance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    boolean existsBy();


    List<Expense> findByUser(User user);

}
