package se.kth.iv1201.iv1201recruitmentapp.util;

/**
 * Class for sending logging events for changing of applications.
 */
public class ApplicationChangeLoggingEvent {
    private int applicationId;


    /**
     * Constructor for the logging event
     *
     * @param applicationId the id of the changed application
     */
    public ApplicationChangeLoggingEvent(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getApplicationId() {
        return applicationId;
    }
}
