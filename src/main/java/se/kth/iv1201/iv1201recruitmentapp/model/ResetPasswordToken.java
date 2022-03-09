package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * The reset password token model as represented by the
 * corresponding database table.
 */
@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "token", nullable = false, unique = true)
    private UUID token;

    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name="person_id", unique = true)
    private Person person;

    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;

    public ResetPasswordToken(){}

    public ResetPasswordToken(UUID token, Person person, long lifetime){
        this.token = token;
        this.person = person;
        this.expirationDate = calculateExpirationDate(lifetime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Sets new expiration date as current date plus an
     * amount of minutes.
     * @param minutes the minutes from now when the token should expire
     */
    public void setNewLifetime(long minutes){
        this.expirationDate = calculateExpirationDate(minutes);
    }

    private Instant calculateExpirationDate(long minutes) {
        return ZonedDateTime.now(ZoneId.of("UTC")).plusSeconds(minutes * 60).toInstant();
    }

}
