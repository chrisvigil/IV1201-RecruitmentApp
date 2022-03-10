package se.kth.iv1201.iv1201recruitmentapp.config;

import org.springframework.boot.actuate.security.AuthenticationAuditListener;
import org.springframework.boot.actuate.security.AuthorizationAuditListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Beans so that Listeners can be implemented for AuditEvents.
 */
@Configuration
public class AuditEventConfiguration {

    /**
     * Used for making authentication listeners possible.
     * @return an authentication listener
     * @throws Exception if no listener could be created.
     */
    @Bean
    public AuthenticationAuditListener authenticationAuditListener() throws Exception {
        return new AuthenticationAuditListener();
    }

    /**
     * Used for making authorization listeners possible.
     * @return an authorization listener
     * @throws Exception if no listener could be created
     */
    @Bean
    public AuthorizationAuditListener authorizationAuditListener() throws  Exception {
        return new AuthorizationAuditListener();
    }
}
