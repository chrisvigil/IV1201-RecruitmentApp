package se.kth.iv1201.iv1201recruitmentapp.config;

import org.springframework.boot.actuate.security.AuthenticationAuditListener;
import org.springframework.boot.actuate.security.AuthorizationAuditListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditEventConfiguration {

    @Bean
    public AuthenticationAuditListener authenticationAuditListener() throws Exception {
        return new AuthenticationAuditListener();
    }

    @Bean
    public AuthorizationAuditListener authorizationAuditListener() throws  Exception {
        return new AuthorizationAuditListener();
    }
}
