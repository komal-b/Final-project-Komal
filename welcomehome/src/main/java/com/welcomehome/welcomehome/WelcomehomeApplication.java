package com.welcomehome.welcomehome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class},
		scanBasePackages = "com.welcomehome")
public class WelcomehomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelcomehomeApplication.class, args);
	}

}
