package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class to handle global errors in the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    /**
     * Handler for connection errors when communicating with the database.
     *
     * @return The database communication error page.
     */
    @ExceptionHandler(JDBCConnectionException.class)
    public String databaseConnectionError() {
        return "error/dbConnectionError";
    }


    @ExceptionHandler(Exception.class)
    public String genericError()
    {
        return "error";
    }
}
