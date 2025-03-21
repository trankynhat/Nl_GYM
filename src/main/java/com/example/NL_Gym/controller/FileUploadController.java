package com.example.NL_Gym.controller;

import com.example.NL_Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUploadController {

    private final String UPLOAD_DIR = "uploads/avatars/";

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file,
                                          @RequestParam("email") String email) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File không được để trống!");
            }

            // Định dạng tên file theo user
            String fileName = email.replaceAll("[^a-zA-Z0-9]", "_") + "_" + System.currentTimeMillis() + ".jpg";
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Tạo thư mục nếu chưa có
            Files.createDirectories(filePath.getParent());

            // Lưu file vào server
            Files.write(filePath, file.getBytes());

            // Đường dẫn URL để lưu vào database
            String fileUrl =  fileName;

            // Cập nhật avatar vào database
            userRepository.updateAvatar(email, fileUrl);

            return ResponseEntity.ok().body(Map.of("message", "Upload thành công!", "url", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Lỗi khi upload file: " + e.getMessage());
        }
    }


}