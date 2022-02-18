package se.kth.iv1201.iv1201recruitmentapp.constraint;

import org.springframework.beans.factory.annotation.Autowired;
import se.kth.iv1201.iv1201recruitmentapp.service.SecurityUserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for {@link CustomUniqueEmailConstraint}
 */
public class CustomUniqueEmailValidator implements ConstraintValidator<CustomUniqueEmailConstraint, String> {
    @Autowired
    private SecurityUserDetailService userService;


    /**
     * Initializes the validator in preparation for {@link #isValid(String, ConstraintValidatorContext)} calls.
     * This method is guaranteed to be called before any use of this instance for validation.
     *
     * @param constraint validation constraint
     */
    @Override
    public void initialize(CustomUniqueEmailConstraint constraint){
        ConstraintValidator.super.initialize(constraint);
    }

    /**
     * Implementation of validation logic
     * @param email the email to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return false if email is already present in the database
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isEmailRegistered(email);
    }
}
