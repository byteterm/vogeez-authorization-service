package net.vogeez.authorization.service.validate;

import net.vogeez.authorization.service.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    //ToDo Change regex to match Password requirements
    public final static Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,30}$");

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        //return PASSWORD_PATTERN.matcher(value).matches();
        return true;
    }
}
