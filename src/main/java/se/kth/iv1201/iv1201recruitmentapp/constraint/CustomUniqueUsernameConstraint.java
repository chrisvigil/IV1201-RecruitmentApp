package se.kth.iv1201.iv1201recruitmentapp.constraint;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates if a username is already registered in database.
 * Validation fails if username is already present in the database
 */
@Documented
@Constraint(validatedBy = CustomUniqueUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUniqueUsernameConstraint {
    String message() default "That username is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
