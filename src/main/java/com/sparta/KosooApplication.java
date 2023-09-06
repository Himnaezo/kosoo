package com.sparta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KosooApplication {

	public static void main(String[] args) {
		SpringApplication.run(KosooApplication.class, args);
	}

}
