package ApplicationBalance.entities;

import jakarta.persistence.*;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
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

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;


    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    public TransactionType type;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


}
