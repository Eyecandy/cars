package no.linska.webapp.responses;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {

    private String token;
    private String tokenType;
    private Long id;
    private String email;
    private List<String> roles;



}
