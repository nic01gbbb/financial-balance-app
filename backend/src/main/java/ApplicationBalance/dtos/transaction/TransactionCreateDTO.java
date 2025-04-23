package ApplicationBalance.dtos.transaction;


import ApplicationBalance.enums.ExpenseType;
import ApplicationBalance.enums.TransactionType;
import ApplicationBalance.validate.EnumValidator;
import ApplicationBalance.validate.ValidExpenseData;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ValidExpenseData
public class TransactionCreateDTO {

    @NotNull(message = "The transaction's value is required")
    @DecimalMin(value = "0.01", message = "The value must be bigger than zero")
    private BigDecimal amount;

    @NotBlank(message = "The description is required")
    private String description;

    @NotNull(message = "The transaction's type is required")
    @NotBlank(message = "The transaction's type is required")
    @EnumValidator(enumClass = TransactionType.class, message = "Transaction type must be INCOME or EXPENSE")
    private String transactionType;  // Alterado para String


    // Expense side
    private String expenseType;
    private String expenseDescription;
}

