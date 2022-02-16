package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

import se.kth.iv1201.iv1201recruitmentapp.constraint.CustomFieldMatchConstraint;
import se.kth.iv1201.iv1201recruitmentapp.constraint.CustomUniqueEmailConstraint;
import se.kth.iv1201.iv1201recruitmentapp.constraint.CustomUniqueUsernameConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Class for registration form backing object
 */
@CustomFieldMatchConstraint.List({
        @CustomFieldMatchConstraint(
                first = "password",
                second = "confirmPassword",
                message = "{userregistrationdto.password.fieldmatch}"),
        @CustomFieldMatchConstraint(
                first = "email",
                second = "confirmEmail",
                message = "{userregistrationdto.email.fieldmatch}")
})
public class UserRegistrationDto {

    @NotBlank(message = "{userregistrationdto.firstname.notblank}")
    @Size(max=255, message = "{userregistrationdto.firstname.size.max}")
    private String firstName;

    @NotBlank(message = "{userregistrationdto.lastname.notblank}")
    @Size(max=255, message = "{userregistrationdto.lastname.size.max}")
    private String lastName;

    @NotBlank(message = "{userregistrationdto.password.notblank}")
    @Size(min = 8, message ="{userregistrationdto.password.size.min}")
    @Size(max = 255, message ="{userregistrationdto.password.size.max}")
    private String password;

    @NotBlank(message = "{userregistrationdto.password.notblank}")
    private String confirmPassword;

    @NotBlank(message = "{userregistrationdto.personnumber.notblank}")
    @Pattern(regexp = "^\\d{8}-\\d{4}$",message = "{userregistrationdto.personnumber.pattern}")
    private String personNumber;

    @NotBlank(message = "{userregistrationdto.email.notblank}")
    @Email(message = "{userregistrationdto.email.invalid}")
    @CustomUniqueEmailConstraint(message = "{userregistrationdto.email.alreadyregistered}")
    private String email;

    @NotBlank(message = "{userregistrationdto.email.notblank}")
    @Email(message = "{userregistrationdto.email.invalid}")
    private String confirmEmail;

    @NotBlank(message = "{userregistrationdto.username.notblank}")
    @Size(max=255,message = "{userregistrationdto.username.size.max}")
    @CustomUniqueUsernameConstraint(message = "{userregistrationdto.username.alreadyregistered}")
    private String username;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }
}
