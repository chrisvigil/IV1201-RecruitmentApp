package se.kth.iv1201.iv1201recruitmentapp.util;

/**
 * Class for sending logging events for changing of applications.
 */
public class ApplicationChangeLoggingEvent {
    private int applicationId;


    /**
     * TODO comment
     * @param applicationId
     */
    public ApplicationChangeLoggingEvent(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getApplicationId() {
        return applicationId;
    }
}
