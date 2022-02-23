package se.kth.iv1201.iv1201recruitmentapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import se.kth.iv1201.iv1201recruitmentapp.controller.CustomAuthenticationFailureHandler;

/**
 * Web Security configuration class.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Sets the password encoder for the application.
     *
     * @return The password encoder.
     */
    @Bean
    public Argon2PasswordEncoder passwordEncoder(){
        return new Argon2PasswordEncoder();
    }

    /**
     * Sets the custom Authentication handler to use on authentication failure.
     *
     * @return The Authentication failure handler.
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    /**
     * Configuration parameters for Spring Security.
     *
     * @param http The Spring Security configuration object.
     * @throws Exception For invalid configurations.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/applicant/**").hasAuthority("applicant")
                    .antMatchers("/recruiter/**").hasAuthority("recruiter")
                    .antMatchers(
                            "/registration**",
                            "/js/**",
                            "/css/**",
                            "/img/**",
                            "/webjars/**",
                            "/error/**",
                            "/login**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
    }
}
