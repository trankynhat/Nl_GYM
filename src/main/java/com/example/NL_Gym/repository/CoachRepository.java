package com.example.NL_Gym.repository;

import com.example.NL_Gym.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Integer> {
    Optional<Coach> findByUserId(Integer userId);


}
