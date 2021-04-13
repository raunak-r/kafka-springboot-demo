package raunakr.kafkaspringbootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSpringbootDemoApplication {

	private static final Logger logging = LoggerFactory.getLogger(KafkaSpringbootDemoApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(KafkaSpringbootDemoApplication.class, args);
		logging.info("Project Running");
	}

}
