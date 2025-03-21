package com.example.NL_Gym;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseTestService {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseTestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void testConnection() {
        try {
            jdbcTemplate.execute("SELECT 1");
            System.out.println("✅ Kết nối MySQL thành công!");
        } catch (Exception e) {
            System.err.println("❌ Lỗi kết nối MySQL: " + e.getMessage());
        }
    }
}
