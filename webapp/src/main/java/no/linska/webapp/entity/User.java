package no.linska.webapp.entity;

import lombok.Data;
import no.linska.webapp.validator.email.ValidEmail;
import no.linska.webapp.validator.password.MatchingPassword;
import no.linska.webapp.validator.password.NoWhiteSpaceAllowed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MatchingPassword(message = "Passordene må matches")
@Entity(name = "user_account")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "enabled")
    private boolean enabled = true;

    private String role = "customer";

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "Epost adresse kan ikke være tom")
    @ValidEmail
    private String email;

    @Column(name = "password")
    @Size(min = 8, max = 60, message = "Passordet må minst ha 8 tegn")
    @NotBlank(message = "Passordet kan ikke være tomt")
    @NoWhiteSpaceAllowed(message = "Mellomrom kan ikke brukes i passord")
    private String password;

    @Transient
    private String matchingPassword;


    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s',email='%s']",
                id, firstName, lastName,email);
    }
}