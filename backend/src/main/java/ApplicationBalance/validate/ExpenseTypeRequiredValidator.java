package ApplicationBalance.validate;

import ApplicationBalance.dtos.transaction.TransactionCreateDTO;
import ApplicationBalance.enums.ExpenseType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Arrays;

public class ExpenseTypeRequiredValidator implements ConstraintValidator<ValidExpenseData, TransactionCreateDTO> {

    @Override
    public boolean isValid(TransactionCreateDTO dto, ConstraintValidatorContext context) {
        if ("EXPENSE".equalsIgnoreCase(dto.getTransactionType())) {
            boolean isValid = true;

            if (dto.getExpenseDescription() == null || dto.getExpenseDescription().isBlank()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Expense description is required when transaction type is EXPENSE")
                        .addPropertyNode("expensedescription") // nome do campo no DTO
                        .addConstraintViolation();
                isValid = false;
            }
            if (dto.getExpenseType() != null && !dto.getExpenseType().isBlank()) {
                boolean isValidEnum = false;
                for (ExpenseType type : ExpenseType.values()) {
                    if (type.name().equals(dto.getExpenseType())) {
                        isValidEnum = true;
                        break;
                    }
                }

                if (!isValidEnum) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate
                                    ("Invalid Expense type. Allowed values are: " +
                                            Arrays.toString(ExpenseType.values()))
                            .addPropertyNode("expenseType")
                            .addConstraintViolation();
                    isValid = false;
                }
            }


            return isValid;
        }

        return true; // Se não for despesa, tá tudo certo
    }

}