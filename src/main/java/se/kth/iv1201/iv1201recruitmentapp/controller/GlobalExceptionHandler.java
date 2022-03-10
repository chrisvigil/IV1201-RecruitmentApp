package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class to handle global errors in the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handler for connection errors when communicating with the database.
     *
     * @return The database communication error page.
     */
    @ExceptionHandler(JDBCConnectionException.class)
    public String databaseConnectionError(JDBCConnectionException exception) {
        logger.error("Communication error with the database: " + exception.toString());
        exception.printStackTrace();
        return "error/dbConnectionError";
    }


    @ExceptionHandler(Exception.class)
    public String genericError(Exception exception)
    {
        logger.error("Generic error:" + exception.toString());
        exception.printStackTrace();
        return "error";
    }
}
