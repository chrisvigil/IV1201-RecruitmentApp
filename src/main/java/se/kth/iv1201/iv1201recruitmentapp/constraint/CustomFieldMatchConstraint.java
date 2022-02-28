package se.kth.iv1201.iv1201recruitmentapp.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates if two given fields match
 * Validation fails if fields do not match
 *
 *<p>
 * Original <a href=https://www.javaguides.net/2019/08/registration-login-example-using-springboot-spring-data-jpa-hibernate-mysql-thymeleaf.html>source</a>
 * </p>
 */
@Documented
@Constraint(validatedBy = CustomFieldMatchValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomFieldMatchConstraint {
    String message() default "Fields do not match";
    Class <?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    /*
     * Accepts a list of field pairs to validate
     */
    @Documented
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface  List { CustomFieldMatchConstraint[] value();};
}
