package ApplicationBalance.validate;

import ApplicationBalance.dtos.transaction.TransactionCreateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpenseTypeRequiredValidator implements ConstraintValidator<ValidExpenseData, TransactionCreateDTO> {

    @Override
    public boolean isValid(TransactionCreateDTO dto, ConstraintValidatorContext context) {
        if ("EXPENSE".equalsIgnoreCase(dto.getTransactionType())) {
            return dto.getExpenseType() != null && !dto.getExpenseType().isBlank();
        }
        return true; // Se não for despesa, tá tudo certo
    }
}
