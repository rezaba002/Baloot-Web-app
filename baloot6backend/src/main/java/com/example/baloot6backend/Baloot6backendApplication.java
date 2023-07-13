package com.example.baloot6backend;

import com.example.baloot6backend.Service.ApiHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Baloot6backendApplication implements CommandLineRunner {

	@Autowired
	private ApiHandler apiHandler;

	public static void main(String[] args) {
		SpringApplication.run(Baloot6backendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		apiHandler.downloadData();
	}
}
