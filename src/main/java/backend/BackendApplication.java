package backend;

import backend.config.ApplicationConfig.EnvLoad;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		EnvLoad.load();
		SpringApplication.run(BackendApplication.class, args);
	}
}
