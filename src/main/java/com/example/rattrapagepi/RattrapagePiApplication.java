package com.example.rattrapagepi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RattrapagePiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RattrapagePiApplication.class, args);
	}

}
