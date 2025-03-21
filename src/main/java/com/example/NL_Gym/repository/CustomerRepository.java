package com.example.NL_Gym.repository;


import com.example.NL_Gym.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
