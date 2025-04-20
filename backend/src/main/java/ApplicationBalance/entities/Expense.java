package ApplicationBalance.entities;

import ApplicationBalance.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "expenses")

public class Expense {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "expense_type", nullable = false)
    @Enumerated(EnumType.STRING) // Usar o valor do nome da enum, ou seja, "INCOME" ou "EXPENSE"
    private ExpenseType expenseType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String description;
    private BigDecimal amount;

    private LocalDateTime due_date;



    // Getters and Setters
}
