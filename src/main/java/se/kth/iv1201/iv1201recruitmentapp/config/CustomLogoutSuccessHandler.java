package se.kth.iv1201.iv1201recruitmentapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom Logout handler so that custom code can be run when a user successfully logs out.
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    /**
     * Should never be called from any other method. This method is only meant to be used by spring security when a logout
     * succeeds.
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.info("User: " + authentication.getName() + "; has logged out");
        response.sendRedirect("/login?logout");
    }
}
