package com.epam.tkach.carrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarRentSpringApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/rentSpring");
		SpringApplication.run(CarRentSpringApplication.class, args);
	}

}
