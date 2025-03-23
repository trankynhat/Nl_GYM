package com.example.NL_Gym.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateClassTemplateRequest {
    private String name;
    private String description;

    @JsonProperty("default_duration")
    private Integer default_duration;
    @JsonProperty("max_participants")
    private Integer max_participants;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDefaultDuration() { return default_duration; }
    public void setDefaultDuration(Integer defaultDuration) { this.default_duration = defaultDuration; }

    public Integer getMaxParticipants() { return max_participants; }
    public void setMaxParticipants(Integer maxParticipants) { this.max_participants = maxParticipants; }


}