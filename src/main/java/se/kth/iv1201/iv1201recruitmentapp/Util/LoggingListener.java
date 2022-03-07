package se.kth.iv1201.iv1201recruitmentapp.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingListener {
    Logger registrationLogger = LoggerFactory.getLogger("Registration");
    Logger passwordResetLogger = LoggerFactory.getLogger("Password-reset");

    @EventListener
    public void onRegistrationEvent(RegistrationLoggingEvent event) {
        String username = event.getUsername();
        registrationLogger.info("User: " + username + " has registered.");
    }

    @EventListener
    public void onResetPasswordEvent(ResetPasswordLoggingEvent event) {
        ResetPasswordLoggingEvent.ResetPasswordLoggingEnum type = event.getType();
        String email = event.getEmail();
        switch (type){
            case REQUEST:
                passwordResetLogger.info("Password request done for user with email: " + email);
                break;
            case SUCCESS:
                passwordResetLogger.info("A user has reset their password.");
                break;
            default:
                passwordResetLogger.error("Something weird happened with a password reset request. Email: " + email);
        }

    }
}
