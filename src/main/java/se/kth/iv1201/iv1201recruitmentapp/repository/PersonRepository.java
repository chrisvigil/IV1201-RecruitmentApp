package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.iv1201recruitmentapp.model.Person;

import java.util.Optional;

/**
 * Defines database access for the person.
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {
    /**
     * Gets the person from the database by username.
     *
     * @param username The username.
     * @return Returns optional, contains type person on success,
     *          otherwise contains null.
     */
    Optional<Person> findByUsername(String username);

    /**
     * Gets the person from the database by email.
     *
     * @param email The email.
     * @return Returns optional, contains type person on success,
     *          otherwise contains null.
     */
    Optional<Person> findByEmail(String email);
}
