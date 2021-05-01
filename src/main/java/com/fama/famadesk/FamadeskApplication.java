package com.fama.famadesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@Configuration
public class FamadeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamadeskApplication.class, args);
	}

}
