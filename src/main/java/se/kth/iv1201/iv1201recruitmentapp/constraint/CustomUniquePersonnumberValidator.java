package se.kth.iv1201.iv1201recruitmentapp.constraint;

import org.springframework.beans.factory.annotation.Autowired;
import se.kth.iv1201.iv1201recruitmentapp.service.SecurityUserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for {@link CustomUniquePersonnumberConstraint}
 */
public class CustomUniquePersonnumberValidator implements ConstraintValidator<CustomUniquePersonnumberConstraint, String> {
    @Autowired
    private SecurityUserDetailService userService;

    /**
     * Initializes the validator in preparation for {@link #isValid(String, ConstraintValidatorContext)})} calls.
     * This method is guaranteed to be called before any use of this instance for validation.
     *
     * @param constraint validation constraint
     */
    @Override
    public void initialize(CustomUniquePersonnumberConstraint constraint) {
        ConstraintValidator.super.initialize(constraint);
    }

    /**
     * Implementation of validation logic
     * @param personnumber the personnumber to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return false if personnumber is already present in the database
     */
    @Override
    public boolean isValid(String personnumber, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isPersonnumberRegistered(personnumber);
    }
}
