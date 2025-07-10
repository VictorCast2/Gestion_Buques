package com.Proyect.GestionBuques.GestionBuques;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionBuquesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionBuquesApplication.class, args);
	}

	@Bean
	public CommandLineRunner init() {
        return null;
    }

}