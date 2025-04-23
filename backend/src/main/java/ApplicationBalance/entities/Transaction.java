package ApplicationBalance.entities;

import ApplicationBalance.enums.TransactionType;
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
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING) // Usar o valor do nome da enum, ou seja, "INCOME" ou "EXPENSE"
    private TransactionType transactionType;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


}
