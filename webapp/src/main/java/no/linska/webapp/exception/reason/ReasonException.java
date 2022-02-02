package no.linska.webapp.exception.reason;

import lombok.Getter;

@Getter
public abstract class ReasonException extends RuntimeException {

    private final Reason reason;

    public ReasonException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public ReasonException(Reason reason, Throwable throwable) {
        super(throwable);
        this.reason = reason;
    }

    public ReasonException(Reason reason, String message, Throwable throwable) {
        super(message, throwable);
        this.reason = reason;
    }


}