package no.linska.webapp.exception;

import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;

public class DataException extends ProcessingException {
    public DataException(Reason reason) {
        super(reason);
    }

    public DataException(Reason reason, String message) {
        super(reason, message);
    }

    public DataException(Reason reason, String message, Throwable cause) {
        super(reason, message, cause);
    }

    public DataException(Reason reason, Throwable cause) {
        super(reason, cause);
    }
}
