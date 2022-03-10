package se.kth.iv1201.iv1201recruitmentapp.util;

/**
 * Class used for events concerning logging of password reset requests
 */
public class ResetPasswordLoggingEvent {
    public enum ResetPasswordLoggingEnum {REQUEST, SUCCESS}

    private final ResetPasswordLoggingEnum type;
    private final String email;

    public ResetPasswordLoggingEvent(ResetPasswordLoggingEnum type, String email) {
        this.type = type;
        this.email = email;
    }

    public ResetPasswordLoggingEnum getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }
}
