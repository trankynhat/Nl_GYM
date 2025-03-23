package com.example.NL_Gym.repository;

import java.util.Optional;

import com.example.NL_Gym.model.Customer;
import com.example.NL_Gym.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findById(Integer integer);

    @Procedure("create_customer_account")
    void createCustomerAccount(
            String email,
            String password,
            String fullName,
            String phone,
            String avatar,
            String birthDate,
            String address
    );
    @Procedure(name = "create_coach_account")
    void createCoachAccount(
            String email,
            String password,
            String fullName,
            String phone,
            String avatar,
            String expertise
    );

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.email = :email")
    void updateAvatar(@Param("email") String email, @Param("avatar") String avatar);



}
