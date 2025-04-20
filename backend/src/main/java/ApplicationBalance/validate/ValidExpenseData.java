package ApplicationBalance.validate;

import ApplicationBalance.validate.ExpenseTypeRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExpenseTypeRequiredValidator.class)
public @interface ValidExpenseData {
    String message() default "Expense type is required for EXPENSE transactions";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
