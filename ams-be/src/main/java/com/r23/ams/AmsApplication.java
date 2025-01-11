package com.r23.ams;

import com.r23.ams.models.entities.*;
import com.r23.ams.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AmsApplication.class, args);
	}

}