package no.linska.webapp.exception;


import no.linska.webapp.exception.reason.Reason;

public class StorageFileNotFoundException extends StorageException {


    public StorageFileNotFoundException(Reason reason) {
        super(reason);
    }

    public StorageFileNotFoundException(Reason reason, String message) {
        super(reason, message);
    }

    public StorageFileNotFoundException(Reason reason, String message, Throwable cause) {
        super(reason, message, cause);
    }

    public StorageFileNotFoundException(Reason reason, Throwable cause) {
        super(reason, cause);
    }
}
