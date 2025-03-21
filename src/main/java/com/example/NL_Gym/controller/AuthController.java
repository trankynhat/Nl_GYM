package com.example.NL_Gym.controller;

import com.example.NL_Gym.dto.AuthRequest;
import com.example.NL_Gym.model.Role;
import com.example.NL_Gym.model.User;
import com.example.NL_Gym.utils.JWTUtil;
import com.example.NL_Gym.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.NL_Gym.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        System.out.println(">>> Login request received for: " + authRequest.getEmail());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            System.out.println(">>> Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }

        System.out.println(">>> Authentication successful!");

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetailsImpl.getEmail();
        Role role = userDetailsImpl.getRole();

        String token = jwtUtil.generateToken(email, role.toString());
        System.out.println(">>> Generated Token: " + token);
        return ResponseEntity.ok(Map.of("token", token));
    }
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getEmail() == null){
            System.out.println(">>> User details not found!");
            return ResponseEntity.status(401).build();
        }
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

}
