package net.vogeez.authorization.service.annotation;

import net.vogeez.authorization.service.validate.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    String message() default "Username must be between 3 and 20 characters long and can only contain letters, numbers and underscores";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
