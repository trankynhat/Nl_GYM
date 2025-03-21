package com.example.NL_Gym;
import com.example.NL_Gym.model.User;
import com.example.NL_Gym.model.Customer;
import com.example.NL_Gym.repository.UserRepository;
import com.example.NL_Gym.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.NL_Gym.model.Role;


import java.time.LocalDate;

@Component
public class TestDatabaseRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    public TestDatabaseRunner(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    public void run(String... args) {
        System.out.println("=== Bắt đầu test User và Customer ===");


        System.out.println("Đã thêm User và Customer vào database!");
    }
}
