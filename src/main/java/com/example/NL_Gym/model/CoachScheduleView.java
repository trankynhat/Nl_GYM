package com.example.NL_Gym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coach_schedule_view")
public class CoachScheduleView {

    @Id
    private Integer classId;  // View không có khóa chính, dùng class_id tạm thời

    private String className;
    private String classDescription;
    private Integer durationMinutes;
    private Integer maxParticipants;
    private String coachName;
    private String coachEmail;
    private String startDate;
    private String daysOfWeek;
    private String startTimes;
}
