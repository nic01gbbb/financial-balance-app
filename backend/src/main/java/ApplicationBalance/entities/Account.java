package ApplicationBalance.entities;

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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal balance;
    private LocalDateTime lastUpdated;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // getters e setters

    // Getters and Setters
}