package systems.tat.authorization.service.annotation;

import systems.tat.authorization.service.validate.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    // ToDo Change message to match username requirements let the user know what is wrong
    // ToDo Adding size check
    String message() default "Username is not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
