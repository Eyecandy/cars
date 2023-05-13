package no.linska.webapp.responses;

import lombok.Data;

import java.util.List;

@Data
public class CodeResponse {
    private String code;
    private List errorMessageList;


    public CodeResponse(String code) {
        this.code = code;
    }

    public CodeResponse(String code,List errorMessageList) {
        this.code = code;
        this.errorMessageList = errorMessageList;
    }

}
