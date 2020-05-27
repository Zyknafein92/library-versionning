package com.library.borrowmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BorrowMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowMicroserviceApplication.class, args);
	}

}
