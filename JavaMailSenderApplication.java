package com.example.JavaMailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JavaMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaMailSenderApplication.class, args);
	}

}
