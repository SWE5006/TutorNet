package com.project.tutornet.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID subjectId;
    private String role;

    @OneToMany(mappedBy = "interest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterestTimeSlot> timeSlots;

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public UUID getSubjectId() { return subjectId; }
    public void setSubjectId(UUID subjectId) { this.subjectId = subjectId; }
    public List<InterestTimeSlot> getTimeSlots() { return timeSlots; }
    public void setTimeSlots(List<InterestTimeSlot> timeSlots) { this.timeSlots = timeSlots; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
} 