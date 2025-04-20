package ApplicationBalance.dtos.transaction;


import ApplicationBalance.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponseDTO {

    private UUID id;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private TransactionType type;
}

