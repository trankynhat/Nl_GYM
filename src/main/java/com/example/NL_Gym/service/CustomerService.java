package com.example.NL_Gym.service;

import com.example.NL_Gym.dto.CustomerRegistrationRequest;
import com.example.NL_Gym.repository.CustomerRepository;
import com.example.NL_Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public CustomerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        userRepository.createCustomerAccount(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),  // Chưa mã hóa, cần xử lý nếu cần
                request.getFullName(),
                request.getPhone(),
                null,  // Avatar có thể null ban đầu
                request.getBirthDate().toString(),
                request.getAddress()
        );
    }
}
