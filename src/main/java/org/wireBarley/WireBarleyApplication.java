package org.wireBarley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WireBarleyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WireBarleyApplication.class, args);
	}

}
