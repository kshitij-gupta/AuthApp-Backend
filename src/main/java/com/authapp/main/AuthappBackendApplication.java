package com.authapp.main;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
 
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.authapp"})
public class AuthappBackendApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(AuthappBackendApplication.class, args);
	}
 
}