package com.project.tutornet.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class InterestTimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String startTime;
    private String endTime;
    private int dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public Interest getInterest() { return interest; }
    public void setInterest(Interest interest) { this.interest = interest; }
} 