package com.example.NL_Gym.service;

import com.example.NL_Gym.model.Coach;
import com.example.NL_Gym.model.User;
import com.example.NL_Gym.repository.ClassRepository;
import com.example.NL_Gym.repository.CoachRepository;
import com.example.NL_Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;


    public String createClass(int templateID, int coachID, Date startDate, String Schedule) {
        return classRepository.createClass(templateID, coachID, startDate, Schedule);
    }
    public Integer getCoachIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user != null) {
            return user.get().getId();
        }
        return null; // Nếu không tìm thấy Coach, trả về null
    }
}
