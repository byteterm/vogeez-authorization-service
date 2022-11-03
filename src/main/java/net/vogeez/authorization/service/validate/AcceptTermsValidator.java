package net.vogeez.authorization.service.validate;

import net.vogeez.authorization.service.annotation.AcceptTerms;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
public class AcceptTermsValidator implements ConstraintValidator<AcceptTerms, Boolean> {

    @Override
    public void initialize(AcceptTerms constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        return value;
    }
}
