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

    /*
     * Password Credentials:
     *
     * Min length: 8 chars
     * Max length: 32 chars
     * One lowercase letter
     * One uppercase letter
     * One special character
     * One number
     *
     * Note: the regex check the input string for blank chars! (Whitespace)
     *
     */
    public final static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^§&+=])(?=\\S+$).{8,32}$");

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        return PASSWORD_PATTERN.matcher(value).matches();
    }
}
