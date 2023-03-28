package com.example.SpringBootPostgresCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories("com.example.SpringBootPostgresCRUD.*")
@SpringBootApplication
// @ComponentScan(basePackages =
// {"com.example.SpringBootPostgresCRUD.service","com.example.SpringBootPostgresCRUD.controller","com.example.SpringBootPostgresCRUD.repo"})
// @EntityScan("com.example.SpringBootPostgresCRUD.entity")
public class SpringBootPostgresCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPostgresCrudApplication.class, args);
	}

}
