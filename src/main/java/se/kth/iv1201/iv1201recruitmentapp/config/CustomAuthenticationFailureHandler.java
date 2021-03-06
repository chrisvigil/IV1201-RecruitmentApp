package se.kth.iv1201.iv1201recruitmentapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom authentication handler so the right error page can be shown in case of non-normal login errors.
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    /**
     * Called when an authentication attempt fails. Redirects to a special error page if connection to the database fails.
     * Otherwise redirects back to the login page with an error param.
     *
     * @param request the request during which the authentication attempt occurred.
     * @param response the response.
     * @param exception the exception which was thrown to reject the authentication request.
     * @throws IOException Not handled in this method so is thrown again
     * @throws ServletException Not handled in this method so is thrown again
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        String url;
        if(exception instanceof InternalAuthenticationServiceException) {
            url = "/error/dbConnectionError";
            logger.error("Communication error with the database: " + exception.toString());
            exception.printStackTrace();
        }
        else {
            url = "/login?error";
        }

        redirectStrategy.sendRedirect(request, response, url);
    }
}
