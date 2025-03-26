package com.example.NL_Gym.dto;
public class CreateClassRequest {
    private int tempclass;
    private String startDate;
    private String schedule;

    // Getters và Setters
    public int getTempclass() {
        return tempclass;
    }

    public void setTempclass(int tempclass) {
        this.tempclass = tempclass;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}