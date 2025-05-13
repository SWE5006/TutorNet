package com.project.tutornet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
public class TutornetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutornetApplication.class, args);
	}

}
