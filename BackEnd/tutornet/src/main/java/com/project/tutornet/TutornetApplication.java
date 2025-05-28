package com.project.tutornet;

import com.project.tutornet.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class TutornetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutornetApplication.class, args);
	}

}
