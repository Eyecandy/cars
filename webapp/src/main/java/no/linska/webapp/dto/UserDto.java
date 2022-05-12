package no.linska.webapp.dto;

import lombok.Data;

@Data
public abstract class UserDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
