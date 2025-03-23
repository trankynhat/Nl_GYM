package com.example.NL_Gym.controller;

import com.example.NL_Gym.dto.CreateClassTemplateRequest;
import com.example.NL_Gym.dto.CreateCoachRequest;
import com.example.NL_Gym.model.User;
import com.example.NL_Gym.repository.UserRepository;
import com.example.NL_Gym.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/addCoach")
    public ResponseEntity<String> addCoach(@RequestBody CreateCoachRequest request) {
        try {
            adminService.registerCoach(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFullName(),
                    request.getPhone(),
                    request.getAvatar(),
                    request.getExpertise(),
                    request.getMaxClassesPerWeek() // Thêm dòng này
            );
            return ResponseEntity.ok("Coach created successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @PostMapping("/addClassTemplate")
    public ResponseEntity<String> createClassTemplate(@RequestBody CreateClassTemplateRequest request) {
        try {
            adminService.createClassTemplate(request);
            return ResponseEntity.ok("Class template created successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
