package se.kth.iv1201.iv1201recruitmentapp.util;

/**
 * Class used for events concerning logging of registration of new users.
 */
public class RegistrationLoggingEvent {

    private final String username;

    public RegistrationLoggingEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
