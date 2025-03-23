package com.example.NL_Gym.repository;

import com.example.NL_Gym.model.ClassTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTemplateRepository extends JpaRepository<ClassTemplate, Long> {
    boolean existsByName(String name);

}
