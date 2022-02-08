package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="person_id")
    private long personId;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="pnr")
    private String pnr;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    private String username;

    public User() {}

    public User(long personId, String name, String surname, String pnr, String email, String password, Role role, String username) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.pnr = pnr;
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoleId() {
        return role;
    }

    public void setRoleId(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
