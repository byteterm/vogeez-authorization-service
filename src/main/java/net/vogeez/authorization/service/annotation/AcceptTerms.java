package net.vogeez.authorization.service.annotation;

import net.vogeez.authorization.service.validate.AcceptTermsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Documented
@Constraint(validatedBy = AcceptTermsValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptTerms {

    String message() default "You must accept the terms and conditions";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
