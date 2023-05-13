package no.linska.webapp.exception.reason;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Reason {
    STORAGE_COULD_NOT_INIT("Could not initialize storage at path: %s", "s0"),
    STORAGE_FILE_NOT_FOUND_EXCEPTION("File %s not found", "s1"),
    STORAGE_MUST_BE_CORRECT_DIRECTORY("Could not save file in %s dir, allowed dir: %s", "s2"),
    STORAGE_COULD_NOT_READ_FILE("Could not read file %s", "s3"),
    STORAGE_FILES_CAN_NOT_BE_READ("Failed to read files", "s4"),

    STORAGE_FILE_CAN_NOT_BE_STORED("File %s can not be stored", "s5"),
    STORAGE_COULD_NOT_STORE_EMPTY_FILE("Could not store empty file", "s6"),

    CAR_BRAND_IS_EMPTY("Carbrand with id %s is Empty", "s7"),
    NO_RETAILERS_IN_CAR_BRAND("No retailers for selected car brand %s","s8"),
    NO_SELLERS_IN_RETAILERS("No sellers where found from retailers %s", "s9"),

    PRICE_REQUEST_DOES_NOT_EXIST("Can not find priceRequest %s", "s10"),
    PRICE_REQUEST_DOES_NOT_BELONG_TO_USER("PriceRequest %s", "s11"),


    PDF_REQUEST_ON_WRONG_CONFIG_METHOD("Request file, but configMethod is not PDF", "s12"),

    PRICE_REQUEST_ORDER_DOES_NOT_EXIST("Can not find priceRequestOrder %s", "s13"),
    PRICE_REQUEST__ORDER_DOES_NOT_BELONG_TO_USER("PriceRequestOrder %s", "s14"),


    DEADLINE_LINE_PASSED("Deadline passed for %s", "s15"),


    NO_ANSWER_PRICE_REQUEST_ORDERS_ON_REQUEST("No offers where made by sellers on request:  %s", "s16"),
    NO_PRICE_REQUEST_ORDERS_ON_REQUEST("PriceRequestOrders are empty for priceRequest:  %s", "s17"),

    NO_SELLER_WITH_THAT_USER_ID("No seller with user id:  %s", "s18"),

    NO_BUYER_WITH_THAT_USER_ID("No buyer with user id:  %s", "s19"),

    PRICE_REQUEST_FIELD_MISSING("PriceRequest id: %s missing field %s", "s20");



    private final String message;
    private final String code;

    public String getMessage(Object... params) {
        return String.format(message, params);
    }
}
