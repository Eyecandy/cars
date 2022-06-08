package no.linska.webapp.responses;

import lombok.Data;

@Data
public class MessageResponse {
    private String message;
    private String code;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
