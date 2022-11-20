package net.vogeez.authorization.service.validate;

import net.vogeez.authorization.service.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * This class is used to validate the {@link Password} annotation.
 * It implements the {@link ConstraintValidator} interface.
 * The {@link Password} class is the annotation class.
 * The {@link String} class is the type
 * The isValid method is used to validate the {@link Password} annotation.
 * Here is the logic to validate the {@link Password} annotation.
 *
 * @see ConstraintValidator
 * @see Password
 * @see String
 *
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
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!§&?+=])(?=\\S+$).{8,32}$");

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
