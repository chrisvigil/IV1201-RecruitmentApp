package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.iv1201recruitmentapp.model.Person;
import se.kth.iv1201.iv1201recruitmentapp.model.ResetPasswordToken;

import java.util.Optional;
import java.util.UUID;

/**
 * Defines database access to the reset_password_token tabel
 */
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {

    /**
     * Gets a ResetPasswordToken object from the database by its UUID identifier
     * @param token The tokens UUID identifier.
     * @return Optional containing token on success otherwise null
     */
    Optional<ResetPasswordToken> findByToken(UUID token);

    /**
     * Gets a ResetPasswordToken object from the database associated with
     * a person table entry
     * @param person The person tabel entry associated with the token
     * @return Optional containing token on success otherwise null
     */
    Optional<ResetPasswordToken> findByPerson(Person person);
}
