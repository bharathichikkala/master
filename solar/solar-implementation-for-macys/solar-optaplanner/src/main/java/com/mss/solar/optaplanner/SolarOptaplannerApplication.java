package com.mss.solar.optaplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SolarOptaplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarOptaplannerApplication.class, args);
	}

}
