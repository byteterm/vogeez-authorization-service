package systems.tat.authorization.service.validate;

import systems.tat.authorization.service.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    //ToDo Change regex to match username requirements
    public final static Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,15}$");

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
