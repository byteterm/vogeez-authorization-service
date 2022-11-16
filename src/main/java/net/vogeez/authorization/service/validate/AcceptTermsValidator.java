package net.vogeez.authorization.service.validate;

import net.vogeez.authorization.service.annotation.AcceptTerms;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class is used to validate the {@link AcceptTerms} annotation.
 * It implements the {@link ConstraintValidator} interface.
 * The {@link AcceptTerms} class is the annotation class.
 * The {@link Boolean} class is the type
 * The isValid method is used to validate the {@link AcceptTerms} annotation.
 * Here is the logic to validate the {@link AcceptTerms} annotation.
 *
 * @see ConstraintValidator
 * @see AcceptTerms
 * @see Boolean
 *
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
