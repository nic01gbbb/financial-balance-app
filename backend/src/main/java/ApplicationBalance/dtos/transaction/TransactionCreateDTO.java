package ApplicationBalance.dtos.transaction;


import ApplicationBalance.entities.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionCreateDTO {

    @NotNull(message = "The transaction's value is required")
    @DecimalMin(value = "0.01", message = "the value must be bigger than zero")
    private BigDecimal amount;

    @NotBlank(message = "The description is required")
    private String description;

    @NotNull(message = "The transaction's type is required")
    private TransactionType type;
}

