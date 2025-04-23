package ApplicationBalance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue
    private UUID id;

    private String description;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Boolean is_paid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
