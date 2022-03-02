package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ResetPasswordVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name="id")
    private Person person;

    private Date expirationDate;

    public ResetPasswordVerificationToken(){}

    public ResetPasswordVerificationToken(String token) {
        this.token = token;
        //TODO this.expirationDate =
    }

    public ResetPasswordVerificationToken(String token, Person person){
        this.token = token;
        this.person = person;
        //TODO this.expirationDate =
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
