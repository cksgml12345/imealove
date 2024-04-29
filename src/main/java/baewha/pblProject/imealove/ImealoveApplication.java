package baewha.pblProject.imealove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ImealoveApplication {
	public static void main(String[] args) {
		SpringApplication.run(ImealoveApplication.class, args);
	}
}
