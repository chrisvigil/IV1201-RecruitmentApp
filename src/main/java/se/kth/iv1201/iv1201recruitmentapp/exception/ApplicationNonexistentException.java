package se.kth.iv1201.iv1201recruitmentapp.exception;

/**
 * Error class when the selected application no longer exists.
 */
public class ApplicationNonexistentException extends RuntimeException {

    /**
     * Constructor for application nonexistent exception.
     *
     * @param message The message to be sent.
     */
    public ApplicationNonexistentException(String message) {
        super(message);
    }
}
