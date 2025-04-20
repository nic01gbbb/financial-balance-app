package ApplicationBalance.dtos.expense;
import ApplicationBalance.enums.ExpenseType;
import ApplicationBalance.validate.EnumValidator;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseCreateDTO {

    @NotNull(message = "Expense type is required")
    @EnumValidator(enumClass = ExpenseType.class, message = "Expense type invalid")
    private ExpenseType expenseType;  // Validação de enum do tipo de despesa

    @NotNull(message = "User is required")
    private Long userId;  // A relação com o usuário. Se for necessário criar um relacionamento, mude para User ao invés de Long

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;
}
