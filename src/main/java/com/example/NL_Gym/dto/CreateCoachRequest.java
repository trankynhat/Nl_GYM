package com.example.NL_Gym.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CreateCoachRequest {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String avatar;
    private String expertise;
    private Integer maxClassesPerWeek = 5; // Mặc định 5 nếu không có giá trị

    // Thêm thủ công các getter
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getExpertise() {
        return expertise;
    }

    public Integer getMaxClassesPerWeek() {
        return maxClassesPerWeek;
    }
}
