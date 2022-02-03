package no.linska.webapp.exception.reason;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Reason code contract
 *
 * @message Base message for the reason. Can be replaced by providing a message in exception
 * @code Every subcategory like Agreement has own prefix (starting from 001)
 * Then every reason in subcategory has own code (starting from 0001)
 * Example: AGREEMENT_ACTIVATION_ERROR should have id 0010001
 * and BANK_NOT_EXIST in next subcategory should have code 0020001
 */
@Getter
@RequiredArgsConstructor
public enum Reason {
    STORAGE_COULD_NOT_INIT("Could not initialize storage at path: %s", "s0"),
    STORAGE_FILE_NOT_FOUND_EXCEPTION("File %s not found", "s1"),
    STORAGE_MUST_BE_CORRECT_DIRECTORY("Could not save file in %s dir, allowed dir: %s", "s2"),
    STORAGE_COULD_NOT_READ_FILE("Could not read file %s", "s3"),
    STORAGE_FILES_CAN_NOT_BE_READ("Failed to read files", "s4"),

    STORAGE_FILE_CAN_NOT_BE_STORED("File %s can not be stored", "s5"),
    STORAGE_COULD_NOT_STORE_EMPTY_FILE("Could not store empty file", "s6");


    private final String message;
    private final String code;

    public String getMessage(Object... params) {
        return String.format(message, params);
    }
}
