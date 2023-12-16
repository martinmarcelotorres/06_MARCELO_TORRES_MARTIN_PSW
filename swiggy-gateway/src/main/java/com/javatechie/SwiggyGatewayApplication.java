package com.javatechie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.javatechie", "com.javatechie.config"})
public class SwiggyGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggyGatewayApplication.class, args);
	}

}
