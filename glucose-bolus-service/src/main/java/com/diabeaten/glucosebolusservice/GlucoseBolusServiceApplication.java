package com.diabeaten.glucosebolusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GlucoseBolusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlucoseBolusServiceApplication.class, args);
	}

}
