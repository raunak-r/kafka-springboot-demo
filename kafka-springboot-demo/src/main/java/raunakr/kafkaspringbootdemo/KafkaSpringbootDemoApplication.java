package raunakr.kafkaspringbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSpringbootDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(KafkaSpringbootDemoApplication.class, args);
		System.out.println("Working");
	}

}
