package no.linska.webapp.exception.reason;

public class ProcessingException extends ReasonException {

    public ProcessingException(Reason reason) {
        super(reason, reason.getMessage());
    }

    public ProcessingException(Reason reason, String message) {
        super(reason, message);
    }

    public ProcessingException(Reason reason, String message, Throwable cause) {
        super(reason, message, cause);
    }

    public ProcessingException(Reason reason, Throwable cause) {
        super(reason, cause);
    }
}