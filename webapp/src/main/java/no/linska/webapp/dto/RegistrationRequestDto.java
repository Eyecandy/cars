package no.linska.webapp.dto;

import lombok.Data;
import no.linska.webapp.validator.email.ValidEmail;
import no.linska.webapp.validator.password.MatchingPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@MatchingPassword(message = "passwords doesn't match")
public class RegistrationRequestDto {
    @NotBlank(message = "passord kan ikke være tomt")
    @ValidEmail(message = "e-post må følge formatet: bruker@din-epost.com")
    private String email;

    @NotBlank(message = "passord kan ikke være tomt eller inneholde kun mellomrom")
    @Size(min = 8, max = 60, message = "passord må inneholde mellom 8 og 60 tegn")

    private String password;


    @NotBlank(message = "bekreftet passord kan ikke være tomt eller inneholde kun mellomrom")
    private String matchingPassword;
}
