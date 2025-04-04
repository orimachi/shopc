package backend.config.ApplicationConfig;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoad {

    private EnvLoad() {
        throw new IllegalStateException("Utility class");
    }

    private static final Dotenv dotenv = Dotenv.load();

    public static void load() {
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
