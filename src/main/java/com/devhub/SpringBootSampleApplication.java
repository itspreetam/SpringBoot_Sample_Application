package com.devhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSampleApplication {

	public static void main(String[] args) {
		System.err.println("::: Sample :::");
		SpringApplication.run(SpringBootSampleApplication.class, args);
	}

}
