package backend.enums;

import lombok.Getter;

import java.text.MessageFormat;
import java.util.function.Function;

@Getter
public enum EMessage {
    CREATE_MESSAGE_ERROR ("Error when create new customer {0} "),
    ROLE_MESSAGE_NOT_FOUND("Role {0}  is not found."),
    USERNAME_ALREADY_EXIST("Username {0} is already taken!"),
    // product message
    DELETE_PRODUCT_SUCCESS("Delete product with id {0} success."),
    UPDATE_PRODUCT_SUCCESS("Update product with id {0} success."),
    SAVE_PRODUCT_SUCCESS("Save product success."),
    // cache message
    GET_CACHE_FAILURE("Failure getting from cache:"),
    PUT_CACHE_FAILURE("Failure putting into cache:"),
    EVICT_CACHE_FAILURE("Failure evicting cache:"),
    CLEAR_CACHE_FAILURE("Failure clearing from cache:");


    private final String pattern;
    private final Function<Object[], String> formatter;

    EMessage(String pattern) {
        this.pattern = pattern;
        this.formatter = args -> MessageFormat.format(pattern, args);
    }

    public String format(Object... args) {
        return formatter.apply(args);
    }
}
