package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ChangePasswordDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.UserRegistrationDto;
import se.kth.iv1201.iv1201recruitmentapp.model.Person;
import se.kth.iv1201.iv1201recruitmentapp.model.ResetPasswordToken;
import se.kth.iv1201.iv1201recruitmentapp.model.Role;
import se.kth.iv1201.iv1201recruitmentapp.repository.PersonRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.ResetPasswordTokenRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.RoleRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Service for storing and retrieving user details.
 * </p>
 *
 *<p>
 * Class is transactional, transactions start on method call and ends when
 * method returns. Transactions rollback if an exception is thrown otherwise
 * transaction is committed. A new transaction is created for eatch methos call.
 * </p>
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResetPasswordTokenRepository passwordTokenRepository;

    @Autowired
    private Argon2PasswordEncoder passwordEncoder;

    @Autowired
    private Environment env;

    /**
     * Gets a Spring Security User by username or email from the person repository.
     *
     * @param username The username or email.
     * @return The user.
     * @throws UsernameNotFoundException If the user was not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

        String regex ="^([A-Za-z0-9+_.-]+)@([a-zA-Z0-9.-]+)([a-zA-Z]{2,6})$";
        Pattern emailPattern = Pattern.compile(regex);
        Matcher matcher = emailPattern.matcher(username);
        Person person;

        if (matcher.matches()) {
            person = personRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        }
        else {
            person = personRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        }
        return createSpringUser(username, person);
    }

    private org.springframework.security.core.userdetails.User createSpringUser(String username, Person person) {
        return new org.springframework.security.core.userdetails.User(
                username,
                person.getPassword(),
                mapRoleToAuthority(person.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role){
        return List.of(role::getName);
    }

    /**
     * Creates a user from the user registration dto and saves it to the database.
     *
     * @param registrationDto The user registration dto.
     */
    public void createUser(UserRegistrationDto registrationDto) {
        Person person = new Person();
        person.setName(registrationDto.getFirstName());
        person.setSurname(registrationDto.getLastName());
        person.setPnr(registrationDto.getPersonNumber());
        person.setEmail(registrationDto.getEmail());
        person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        person.setUsername(registrationDto.getUsername());

        // RoleId 2 corresponds to "applicant"
        person.setRole(roleRepository.getRoleById(2).orElseThrow(
                () -> new DataSourceLookupFailureException("Could not find role in database")
        ));

        personRepository.save(person);
    }

    /**
     * Generates a reset password token and stores it in the database.
     * If there is already a password token in the database for that user then
     * it will be overwritten with a new one.
     * @param email The email for the user for which to store the reset password token
     * @return If the user is present in the database a password token is returned
     */
    public Optional<String> createPasswordResetToken(String email){
        Optional<Person> person = personRepository.findByEmail(email);
        if(person.isPresent()) {
            UUID token = UUID.randomUUID();
            ResetPasswordToken tokenEntry;
            Optional<ResetPasswordToken> oldTokenEntry = passwordTokenRepository.findByPerson(person.get());

            long lifetime = Long.parseLong(env.getProperty("resetpassword.token.lifetime"));
            
            if(oldTokenEntry.isPresent()){
                tokenEntry = oldTokenEntry.get();
                tokenEntry.setToken(token);
                tokenEntry.setNewLifetime(lifetime);
            } else {
                tokenEntry = new ResetPasswordToken(token, person.get(), lifetime);
            }
            passwordTokenRepository.save(tokenEntry);
            return Optional.of(token.toString());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if password token is database and has not expired.
     * Expired token will be deleted.
     *
     * @param token The token to validate
     * @return true if token is in database and not expired
     */
    public Boolean isPasswordResetTokenValid(String token) {
        Optional<ResetPasswordToken> resetPasswordToken;
        try {
            resetPasswordToken = passwordTokenRepository.findByToken(UUID.fromString(token));
        } catch (IllegalArgumentException ex) {
            return false;
        }
        if(resetPasswordToken.isPresent()) {
            Instant expirationDate = resetPasswordToken.get().getExpirationDate();
            Instant now = ZonedDateTime.now(ZoneId.of("UTC")).toInstant();
            if (expirationDate.isAfter(now)){
                return true;
            } else {
                passwordTokenRepository.delete(resetPasswordToken.get());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Changes the password of the user associated with a token
     *
     * @param changePasswordDto contains the token and the new password
     * @return true if password is reset
     */
    public boolean resetPasswordWithToken(ChangePasswordDto changePasswordDto){
        Optional<ResetPasswordToken> resetPasswordToken = passwordTokenRepository.findByToken(UUID.fromString(changePasswordDto.getToken()));
        if(resetPasswordToken.isPresent()){
            Person person = resetPasswordToken.get().getPerson();
            person.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
            personRepository.save(person);
            passwordTokenRepository.delete(resetPasswordToken.get());
            return true;
        }
        return false;
    }

    /**
     * Checks if email is registered in database
     * @param email the email
     * @return true if email is in database
     */
    public boolean isEmailRegistered(String email){
        return personRepository.findByEmail(email).isPresent();
    }

    /**
     * Checks if username is registered in database
     * @param username the username
     * @return true if username is in database
     */
    public boolean isUsernameRegistered(String username){
        return personRepository.findByUsername(username).isPresent();
    }

    /**
     * Checks if personnumber is registered in database
     * @param personnumber the personnumber
     * @return true if personnumber is in database
     */
    public boolean isPersonnumberRegistered(String personnumber){
        return personRepository.findByPnr(personnumber).isPresent();
    }
}
