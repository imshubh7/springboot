package com.sdelab.sdelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SdelabApplication {
	public static void main(String[] args) {
		SpringApplication.run(SdelabApplication.class, args);
	}
}
