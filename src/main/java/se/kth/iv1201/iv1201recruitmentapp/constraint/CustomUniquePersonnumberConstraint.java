package se.kth.iv1201.iv1201recruitmentapp.constraint;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates if a personnumber is already registered in database.
 * Validation fails if personnumber is already present in the database
 */
@Documented
@Constraint(validatedBy = CustomUniquePersonnumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUniquePersonnumberConstraint {
    String message() default "That personnumber is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
