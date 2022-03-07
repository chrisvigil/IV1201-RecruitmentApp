package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

import se.kth.iv1201.iv1201recruitmentapp.constraint.CustomFieldMatchConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class for reset change password form backing object
 */
 @CustomFieldMatchConstraint(
                first = "password",
                second = "confirmPassword",
                message = "{userregistrationdto.password.fieldmatch}")
public class ChangePasswordDto {

    @NotBlank(message = "{userregistrationdto.password.notblank}")
    @Size(min = 8, message ="{userregistrationdto.password.size.min}")
    @Size(max = 255, message ="{userregistrationdto.password.size.max}")
    private String password;

    @NotBlank(message = "{userregistrationdto.password.notblank}")
    private String confirmPassword;

    @NotBlank
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
