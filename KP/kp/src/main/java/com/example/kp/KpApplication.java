package com.example.kp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KpApplication {

	public static void main(String[] args) {
		SpringApplication.run(KpApplication.class, args);
	}

}
