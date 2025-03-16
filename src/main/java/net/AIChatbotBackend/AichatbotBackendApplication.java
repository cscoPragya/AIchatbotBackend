package net.AIChatbotBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "net.AIChatbotBackend")
public class AichatbotBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AichatbotBackendApplication.class, args);
	}

}
