package com.sparta.springboards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringboardsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringboardsApplication.class, args);
	}
}
