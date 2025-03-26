package com.example.NL_Gym.repository;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public class ClassRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String createClass(int templateId, int coachUserId, Date startDate, String scheduleJson) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("CreateClassWithSchedule");

            // Đăng ký tham số IN
            query.registerStoredProcedureParameter("p_template_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_coach_user_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_start_date", Timestamp.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_schedule", String.class, ParameterMode.IN);

            // Đăng ký tham số OUT
            query.registerStoredProcedureParameter("p_class_id", Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_status_message", String.class, ParameterMode.OUT);

            // Set giá trị cho tham số IN
            query.setParameter("p_template_id", templateId);
            query.setParameter("p_coach_user_id", coachUserId);
            query.setParameter("p_start_date", new Timestamp(startDate.getTime())); // Chuyển Date -> Timestamp
            query.setParameter("p_schedule", scheduleJson);

            // Thực thi Stored Procedure
            query.execute();

            // Lấy kết quả từ tham số OUT
            Integer classId = (Integer) query.getOutputParameterValue("p_class_id");
            String statusMessage = (String) query.getOutputParameterValue("p_status_message");

            return statusMessage != null ? statusMessage : "Không có phản hồi từ stored procedure";
        } catch (Exception e) {
            return "Lỗi khi tạo lớp học: " + e.getMessage();
        }
    }
}
