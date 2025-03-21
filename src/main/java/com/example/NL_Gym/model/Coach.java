package com.example.NL_Gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coaches")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String expertise;

    @Column(name = "max_classes_per_week", nullable = false)
    private int maxClassesPerWeek = 5; // Default là 5 nếu không set giá trị

    // Constructor đầy đủ
    public Coach(User user, String expertise, int maxClassesPerWeek) {
        this.user = user;
        this.expertise = expertise;
        this.maxClassesPerWeek = maxClassesPerWeek;
    }
    public Coach(){}
}
