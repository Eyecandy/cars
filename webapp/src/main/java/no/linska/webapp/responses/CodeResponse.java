package no.linska.webapp.responses;

import lombok.Data;

@Data
public class CodeResponse {
    private String code;


    public CodeResponse(String code) {
        this.code = code;
    }

}
