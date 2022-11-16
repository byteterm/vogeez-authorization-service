package net.vogeez.authorization.service.annotation;

import net.vogeez.authorization.service.validate.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password min length is 8 and max length is 32," +
            "must contain at least one lowercase, uppercase, special character and number!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
