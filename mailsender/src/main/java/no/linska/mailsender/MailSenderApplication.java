package no.linska.mailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("no.linska.properties")
public class MailSenderApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(MailSenderApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

	}

}
