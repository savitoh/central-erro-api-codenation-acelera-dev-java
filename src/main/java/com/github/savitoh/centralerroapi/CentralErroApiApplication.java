package com.github.savitoh.centralerroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class CentralErroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralErroApiApplication.class, args);
	}

}
