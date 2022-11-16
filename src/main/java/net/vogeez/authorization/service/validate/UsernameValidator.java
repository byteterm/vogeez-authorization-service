package net.vogeez.authorization.service.validate;

import net.vogeez.authorization.service.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * This class is used to validate the {@link Username} annotation.
 * It implements the {@link ConstraintValidator} interface.
 * The {@link Username} class is the annotation class.
 * The {@link String} class is the type
 * The isValid method is used to validate the {@link Username} annotation.
 * Here is the logic to validate the {@link Username} annotation.
 *
 * @see ConstraintValidator
 * @see Username
 * @see String
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    public static final Pattern USERNAME_PATTERN = Pattern.compile("^\\w{3,15}$");

    @Override
    public void initialize(Username constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        return USERNAME_PATTERN.matcher(value).matches();
    }
}
