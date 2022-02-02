package no.linska.webapp.exception;

import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;

public class StorageException extends ProcessingException {


	public StorageException(Reason reason) {
		super(reason);
	}

	public StorageException(Reason reason, String message) {
		super(reason, message);
	}

	public StorageException(Reason reason, String message, Throwable cause) {
		super(reason, message, cause);
	}

	public StorageException(Reason reason, Throwable cause) {
		super(reason, cause);
	}
}
