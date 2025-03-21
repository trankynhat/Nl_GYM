package com.example.NL_Gym;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.NL_Gym.DatabaseTestService;
@SpringBootApplication
public class NlGymApplication implements CommandLineRunner {
	private DatabaseTestService databaseTestService;
	public NlGymApplication(DatabaseTestService databaseTestService) {
		this.databaseTestService = databaseTestService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NlGymApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		databaseTestService.testConnection();
	}
}
