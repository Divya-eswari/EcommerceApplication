package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaRepositories("com.example.demo.model.persistence.repositories")
@EntityScan("com.example.demo.model.persistence")

//adding exclusion to the default security auto configuration class provided by spring Boot
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SareetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SareetaApplication.class, args);
	}


	//adding bean of BCryptPasswordEncoder to ensure security
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}





}
