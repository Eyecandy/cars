package no.linska.webapp.dto;

import lombok.Data;

@Data
public class BuyerUserDto extends UserDto {

    private String city;

    private Integer postBox;

    private String streetName;

    private String streetNumber;
}
