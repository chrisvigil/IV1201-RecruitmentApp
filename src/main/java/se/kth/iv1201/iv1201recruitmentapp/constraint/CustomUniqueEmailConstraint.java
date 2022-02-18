package se.kth.iv1201.iv1201recruitmentapp.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * Validates if email is already registered in database.
 * Validation fails if email is already present in the database
 */
@Documented
@Constraint(validatedBy = CustomUniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUniqueEmailConstraint {
    String message() default "That email is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
