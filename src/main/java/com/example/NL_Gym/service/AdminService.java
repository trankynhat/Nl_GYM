package com.example.NL_Gym.service;

import com.example.NL_Gym.dto.CreateClassTemplateRequest;
import com.example.NL_Gym.model.ClassTemplate;
import com.example.NL_Gym.model.Coach;
import com.example.NL_Gym.model.Role;
import com.example.NL_Gym.model.User;
import com.example.NL_Gym.repository.ClassTemplateRepository;
import com.example.NL_Gym.repository.CoachRepository;
import com.example.NL_Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private ClassTemplateRepository classTemplateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Sử dụng @Autowired để inject PasswordEncoder

    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }


    @Transactional
    public void registerCoach(
            String email,
            String password,
            String fullName,
            String phone,
            String avatar,
            String expertise,
            Integer maxClassesPerWeek
    ) {
        // Hash mật khẩu trước khi lưu
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User(email, hashedPassword, fullName, phone, avatar, Role.COACH);
        userRepository.save(user);

        if (maxClassesPerWeek == null) {
            maxClassesPerWeek = 5; // Mặc định là 5 nếu không set
        }

        Coach coach = new Coach(user, expertise, maxClassesPerWeek);
        coachRepository.save(coach);
    }
    public void createClassTemplate(CreateClassTemplateRequest request) {
        String sql = "CALL create_class_template(?, ?, ?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getDescription(), request.getDefaultDuration(), request.getMaxParticipants());
    }
}
