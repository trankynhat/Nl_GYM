package com.example.NL_Gym.controller;

import com.example.NL_Gym.dto.CustomerRegistrationRequest;
import com.example.NL_Gym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        try {
            customerService.registerCustomer(request);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
