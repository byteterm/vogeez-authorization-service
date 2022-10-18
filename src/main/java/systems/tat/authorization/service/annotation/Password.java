package systems.tat.authorization.service.annotation;

import systems.tat.authorization.service.validate.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    //ToDo Change message to match Password requirements let the user know what is wrong
    String message() default "Password is not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
