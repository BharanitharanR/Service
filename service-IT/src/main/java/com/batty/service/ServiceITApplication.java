package com.batty.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component("ServiceITBean")
@ComponentScan(basePackages =  "com.batty")
@SpringBootApplication
public class ServiceITApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceITApplication.class, args);
	}

}
