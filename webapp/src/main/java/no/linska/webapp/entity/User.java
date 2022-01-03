package no.linska.webapp.entity;

import no.linska.webapp.validator.email.ValidEmail;
import no.linska.webapp.validator.password.MatchingPassword;
import no.linska.webapp.validator.password.NoWhiteSpaceAllowed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MatchingPassword(message = "passwords must match")
@Entity(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;


    @Column(name = "email",unique = true)
    @NotBlank(message = "Epost adresse kan ikke være tom")
    @ValidEmail
    private String email;

    @Column(name = "password")
    @Size(min = 8,max = 30, message = "Passordet må være mellom 8 og 30 tegn")
    @NotBlank(message = "Passordet kan ikke være tomt")
    @NoWhiteSpaceAllowed(message = "Mellomrom kan ikke brukes i passord")
    private String password;


    private String matchingPassword;

    public String getEmail() {
        return email;
    };


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s',email='%s']",
                id, firstName, lastName,email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}