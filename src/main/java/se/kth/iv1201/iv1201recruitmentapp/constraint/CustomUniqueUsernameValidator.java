package se.kth.iv1201.iv1201recruitmentapp.constraint;

import org.springframework.beans.factory.annotation.Autowired;
import se.kth.iv1201.iv1201recruitmentapp.service.SecurityUserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for {@link CustomUniqueUsernameConstraint}
 */
public class CustomUniqueUsernameValidator implements ConstraintValidator<CustomUniqueUsernameConstraint, String> {
    @Autowired
    private SecurityUserDetailService userService;

    /**
     * Initializes the validator in preparation for {@link #isValid(String, ConstraintValidatorContext)})} calls.
     * This method is guaranteed to be called before any use of this instance for validation.
     *
     * @param constraint validation constraint
     */
    @Override
    public void initialize(CustomUniqueUsernameConstraint constraint) {
        ConstraintValidator.super.initialize(constraint);
    }

    /**
     * Implementation of validation logic
     * @param username the username to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return false if username is already present in the database
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isUsernameRegistered(username);
    }
}
