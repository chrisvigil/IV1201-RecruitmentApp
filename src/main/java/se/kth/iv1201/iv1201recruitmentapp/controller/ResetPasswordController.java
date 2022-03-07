package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.kth.iv1201.iv1201recruitmentapp.Util.ResetPasswordLoggingEvent;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ChangePasswordDto;
import se.kth.iv1201.iv1201recruitmentapp.service.EmailService;
import se.kth.iv1201.iv1201recruitmentapp.service.SecurityUserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller for pages associated with resetting password
 */
@Controller
public class ResetPasswordController {

    @Autowired
    private SecurityUserDetailService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Creates the change password form backing object
     * @return the change password form backing object
     */
    @ModelAttribute("changedPassword")
    public ChangePasswordDto changePasswordDto() {
        return new ChangePasswordDto();
    }

    /**
     * Handles get request for the reset password page
     * @return the rest password page
     */
    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return "resetPassword/request";
    }

    /**
     * Handles the reset password post action
     * @param email The email address of the user for which a password reset request has been created,
     * @param locale The current local
     * @param request the httpServeletRequest object
     * @return the request submitted page
     */
    @PostMapping("/resetPassword")
    public String submitResetPasswordForm(@ModelAttribute("email") String email, Locale locale, HttpServletRequest request) {
        Optional<String> token = userService.createPasswordResetToken(email);
        if(token.isPresent()) {
            String subject = messageSource.getMessage("resetpassword.email.subject", null, locale);
            String resetPasswordURL = buildResetPasswordUrl(request, token.get());
            Object[] args = new Object[] {resetPasswordURL};
            String body = messageSource.getMessage("resetpassword.email.messagebody", args, locale);
            emailService.sendEmail(email, subject, body);
        }

        publisher.publishEvent(new ResetPasswordLoggingEvent(ResetPasswordLoggingEvent.ResetPasswordLoggingEnum.REQUEST, email));
        return "resetPassword/requestSubmitted";
    }

    private String buildResetPasswordUrl(HttpServletRequest request, String token){
        String baseURL = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
        return baseURL + "/changePassword?token=" + token;
    }

    /**
     * Handels get request for the change password page, only request
     * with valid tokens passed as a query string allow access to the
     * change password page. Invalid tokens redirect to the reset password
     * request form.
     * @param model Used to pass attributes to the view
     * @param token the reset password token passed as a query string, should be a valid UUID
     * @return the change password page for valid tokens.
     */
    @GetMapping("/changePassword")
    public String showChangePasswordForm(Model model, @RequestParam("token") String token) {
        Boolean tokenIsValid = userService.isPasswordResetTokenValid(token);
        if(tokenIsValid) {
            model.addAttribute("token", token);
            return "resetPassword/change";
        } else {
            return "redirect:/resetPassword?invalidToken";
        }
    }

    /**
     * Handels the reset password post action
     * @param changePasswordDto Contains the results of the change password form
     * @param result Contains the changePasswordDto validation result
     * @param model Used to pass attributes to the view
     * @return change success page if change is successful,
     * change password page is form contains errors
     * or reset password page if token is invalid.
     */
    @PostMapping("/changePassword")
    public String submitChangePasswordForm(@ModelAttribute("changedPassword") @Valid ChangePasswordDto changePasswordDto, BindingResult result, Model model){
        Boolean tokenIsValid = userService.isPasswordResetTokenValid(changePasswordDto.getToken());
        if(!tokenIsValid)
            return "redirect:/resetPassword?invalidToken";

        if(result.hasErrors()){
            String token = changePasswordDto.getToken();
            model.addAttribute("token", token);
            return "resetPassword/change";
        }


        boolean success = userService.resetPasswordWithToken(changePasswordDto);

        if(success) {
            publisher.publishEvent(new ResetPasswordLoggingEvent(ResetPasswordLoggingEvent.ResetPasswordLoggingEnum.SUCCESS, ""));
            return "resetPassword/changeSuccess";
        }
        else
            return "redirect:/resetPassword?invalidToken";
    }

}
