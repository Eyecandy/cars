package no.linska.webapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
