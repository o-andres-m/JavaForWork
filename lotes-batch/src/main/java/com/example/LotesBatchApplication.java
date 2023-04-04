package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class LotesBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotesBatchApplication.class, args);
	}

}
