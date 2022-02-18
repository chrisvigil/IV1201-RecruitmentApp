package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.UserRegistrationDto;
import se.kth.iv1201.iv1201recruitmentapp.model.Person;
import se.kth.iv1201.iv1201recruitmentapp.model.Role;
import se.kth.iv1201.iv1201recruitmentapp.repository.PersonRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.RoleRepository;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for storing and retrieving user details.
 */
@Service
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Argon2PasswordEncoder passwordEncoder;

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
     * @return true if username is in database
     */
    public boolean isPersonnumberRegistered(String personnumber){
        return personRepository.findByPnr(personnumber).isPresent();
    }
}
