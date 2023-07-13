package com.example.Baloot5Backend;

import com.example.Baloot5Backend.model.Store;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Baloot5BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Baloot5BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Store store = Store.getInstance();
	}
}
