package com.diabeaten.informationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableDiscoveryClient
@SpringBootApplication
public class InformationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformationServiceApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
	}

}
