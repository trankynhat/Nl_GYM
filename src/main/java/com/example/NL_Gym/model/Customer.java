package com.example.NL_Gym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Setter
    private LocalDate birthDate;
    private String address;

    public Customer() {}
    public Customer(User user, LocalDate birthDate, String address) {

        this.user = user;
        this.birthDate = birthDate;
        this.address = address;

    }

}
