package se.kth.iv1201.iv1201recruitmentapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Class with event listeners used to do logging when relevant logging events are published from methods
 * that needs information logged.
 */
@Component
public class LoggingListener {
    Logger registrationLogger = LoggerFactory.getLogger("Registration");
    Logger passwordResetLogger = LoggerFactory.getLogger("Password-reset");
    Logger applicationChangeLogger = LoggerFactory.getLogger("application-change");

    /**
     * Listens for registration logging events published when a new user is registered.
     * @param event logging event
     */
    @EventListener
    public void onRegistrationEvent(RegistrationLoggingEvent event) {
        String username = event.getUsername();
        registrationLogger.info("User: " + username + " has registered.");
    }

    /**
     * Listens for reset password logging events published when a password reset request are requested or the password
     * are changed based on the token from an earlier request.
     * @param event password reset event
     */
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

    /**
     * Listens for changes made to an application events published when a recruiter changes the status on an application.
     * @param event application change event
     */
    @EventListener
    public void onApplicationChangeLogger(ApplicationChangeLoggingEvent event) {
        String loggingString ="Application: " + event.getApplicationId() + " was changed";
        String currenUser = getCurrentUserName();
        if (currenUser != null) {
            loggingString = loggingString + " by recruiter: " + currenUser;
        }
        applicationChangeLogger.info(loggingString);
    }

    private String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (! (authentication instanceof AnonymousAuthenticationToken) ) {
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }
}
