package se.kth.iv1201.iv1201recruitmentapp.exception;

/**
 * Error class when an invalid name format is given to the application search.
 */
public class ApplicationsNameSearchFormatException extends RuntimeException {

    /**
     * Constructor for application name search format exception.
     *
     * @param message The message to be sent.
     */
    public ApplicationsNameSearchFormatException(String message) {
        super(message);
    }
}
