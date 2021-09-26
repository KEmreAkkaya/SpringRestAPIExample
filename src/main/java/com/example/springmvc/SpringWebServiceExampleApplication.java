package com.example.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.springmvc.security.ApplicationProperties;

@SpringBootApplication
public class SpringWebServiceExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebServiceExampleApplication.class, args);
	}

	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() 
	{
		return new SpringApplicationContext();
	}
	

	@Bean(name = "ApplicationProperties")
	public ApplicationProperties getApplicationProperties() 
	{
		return new ApplicationProperties();
	}
}
