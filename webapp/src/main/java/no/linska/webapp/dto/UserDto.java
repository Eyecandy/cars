package no.linska.webapp.dto;

import lombok.Data;
import no.linska.webapp.validator.email.ValidEmail;
import no.linska.webapp.validator.password.MatchingPassword;
import no.linska.webapp.validator.password.NoWhiteSpaceAllowed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@MatchingPassword(message = "passwords doesn't match")
public class UserDto {
    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    @Size(min = 8, max = 60, message = "Passordet må minst ha 8 tegn")
    @NoWhiteSpaceAllowed(message = "Mellomrom kan ikke brukes i passord")
    private String password;


    @NotBlank(message = "Passordet kan ikke være tomt")
    private String matchingPassword;
}
