package com.example.NL_Gym.controller;

import com.example.NL_Gym.dto.CreateClassRequest;
import com.example.NL_Gym.model.Coach;
import com.example.NL_Gym.repository.ClassTemplateRepository;
import com.example.NL_Gym.service.ClassService;
import com.example.NL_Gym.service.ClassTemplateService;
import com.example.NL_Gym.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/coach")
@PreAuthorize("hasRole('COACH')")
public class CoachController {


    @Autowired
    private ClassService classService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private  ClassTemplateService classTemplateService;


    @PostMapping("/create-class")
    public ResponseEntity<?> createClass(
            @RequestBody CreateClassRequest requestBody,
            HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Invalid token");
            }
            token = token.substring(7); // Bỏ "Bearer " để lấy token thực sự
            String email = jwtUtil.extractEmail(token);

            // Lấy ID của Coach từ email
            int ID = classService.getCoachIdByEmail(email);

            // Parse startDate từ String thành Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(requestBody.getStartDate());

            // Gọi service để tạo lớp học
            classService.createClass(
                    requestBody.getTempclass(),
                    ID,
                    parsedStartDate,
                    requestBody.getSchedule()
            );

            return ResponseEntity.ok("Class created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllTemplate")
    public ResponseEntity<?> getAllTemplate(){
        return ResponseEntity.ok(classTemplateService.getAllTemplates());
    }

}
