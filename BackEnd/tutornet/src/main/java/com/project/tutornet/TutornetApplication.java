package com.project.tutornet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication ()
public class TutornetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutornetApplication.class, args);
	}

}
