package com.example.NL_Gym.model;

import jakarta.persistence.*;

@Entity
@Table(name = "class_templates")
public class ClassTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int default_duration;

    @Column(nullable = false)
    private int max_participants;

    // Getters và Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDefaultDuration() { return default_duration; }
    public void setDefaultDuration(int defaultDuration) { this.default_duration = defaultDuration; }

    public int getMaxParticipants() { return max_participants; }
    public void setMaxParticipants(int maxParticipants) { this.max_participants = maxParticipants; }


}
