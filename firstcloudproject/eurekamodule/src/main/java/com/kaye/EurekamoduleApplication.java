package com.kaye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekamoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekamoduleApplication.class, args);
	}
}
