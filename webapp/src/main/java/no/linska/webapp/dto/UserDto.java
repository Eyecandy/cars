package no.linska.webapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public abstract class UserDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
}
