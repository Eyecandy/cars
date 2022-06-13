package no.linska.webapp.responses;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpCodeDescription {
    SUCCESS("SUCCESS",new CodeResponse("success"),200),
    CREATED("CREATED",new CodeResponse("created"), 201),
    CONFLICT_EMAIL_EXISTS("CONFLICT: email exists", new CodeResponse("reg_conflict"),409);


    private final String description;
    private final CodeResponse codeResponse;
    private final int httpCode;

    public String getDescription(Object... params) {
        return String.format(description, params);
    }
}
