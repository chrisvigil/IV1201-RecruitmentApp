package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The person object model as represented by the
 * corresponding database table.
 */
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "pnr")
    private String pnr;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "username")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reset_password_token_id")
    private ResetPasswordToken resetPasswordToken;

    @OneToMany(mappedBy = "person")
    private Set<Availability> availabilities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<CompetenceProfile> competenceProfiles = new LinkedHashSet<>();

    public Set<CompetenceProfile> getCompetenceProfiles() {
        return competenceProfiles;
    }

    public void setCompetenceProfiles(Set<CompetenceProfile> competenceProfiles) {
        this.competenceProfiles = competenceProfiles;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public ResetPasswordToken getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(ResetPasswordToken resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}