package ApplicationBalance.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private EnumValidator annotation;

    @Override
    public void initialize(EnumValidator annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Se quiser obrigar preenchimento, combine com @NotNull
        }

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        for (Object enumValue : enumValues) {
            if (value.equalsIgnoreCase(enumValue.toString())) {
                return true;
            }
        }

        return false;
    }
}
