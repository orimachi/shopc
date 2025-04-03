package backend.utils.app;

import backend.enums.EMessage;


public class MethodUtils {

    private MethodUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatMessage(EMessage message, Object... args) {
        return message.format(args);
    }
}
