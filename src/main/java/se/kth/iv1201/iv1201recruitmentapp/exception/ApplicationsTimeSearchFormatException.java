package se.kth.iv1201.iv1201recruitmentapp.exception;

/**
 * Error class when an invalid time format is given to the application search.
 */
public class ApplicationsTimeSearchFormatException extends RuntimeException {

    /**
     * Constructor for application time search format exception.
     *
     * @param message The message to be sent.
     */
    public ApplicationsTimeSearchFormatException(String message) {
        super(message);
    }
}
