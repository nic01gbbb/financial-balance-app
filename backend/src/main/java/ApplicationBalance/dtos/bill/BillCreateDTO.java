package ApplicationBalance.dtos.bill;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class BillCreateDTO {

    @NotBlank(message = "Description is required")
    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be today or in the future")
    private LocalDate dueDate;
}
