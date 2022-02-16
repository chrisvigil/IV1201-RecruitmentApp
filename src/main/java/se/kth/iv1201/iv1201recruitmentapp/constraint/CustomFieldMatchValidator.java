package se.kth.iv1201.iv1201recruitmentapp.constraint;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Validator for {@link CustomFieldMatchConstraint}
 */
public class CustomFieldMatchValidator implements ConstraintValidator<CustomFieldMatchConstraint, Object> {

    private String firstField;
    private String secondField;

    /**
     * Initializes the validator in preparation for {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * This method is guaranteed to be called before any use of this instance for validation.
     *
     * @param constraint validation constraint
     */
    @Override
    public void initialize(final CustomFieldMatchConstraint constraint) {
        firstField = constraint.first();
        secondField = constraint.second();
    }

    /**
     * Implementation of validation logic
     * @param value the object to validate, contains two objects to compare
     * @param constraintValidatorContext  context in which the constraint is evaluated
     * @return false if objects do not match
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Object firstObject = BeanUtils.getProperty(value, firstField);
            final Object secondObject = BeanUtils.getProperty(value, secondField);
            return firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }
}
