package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

/**
 * Controller for some error page handling
 */
@Controller
public class ErrorController {
    /**
     * Mapping for database connection error page needed for Authentication error handling
     *
     * @return The error page.
     */
    @GetMapping("/error/dbConnectionError")
    public String dbError () { return "/error/dbConnectionError";}


    /**
     * Test controller to show that if error is thrown the right error handler will take care of it.
     * @return nothing, an exception is thrown.
     */
    @GetMapping("/error/test")
    public String er () {
        SQLException test = new SQLException("sql test");
        throw new JDBCConnectionException("test", test);
    }
}
