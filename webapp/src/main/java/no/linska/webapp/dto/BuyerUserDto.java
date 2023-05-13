package no.linska.webapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BuyerUserDto extends UserDto {

    @NotNull
    private String city;

    @NotNull
    private Integer postBox;

    @NotNull
    private String streetName;

    @NotNull
    private String streetNumber;
}
