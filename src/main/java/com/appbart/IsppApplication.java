package com.appbart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.appbart.classes","com.appbart.controller"})
public class IsppApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsppApplication.class, args);
	}

}
