package com.project.tutornet.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private UUID userId;
    private String email;
    private String fullName;
    private String role; // "STUDENT" or "TUTOR"
    
    // Common fields
    private String bio;
    private String education;
    private String experience;
    
    // Student specific fields
    private List<String> interestedSubjects;
    private List<String> topics;
    private Double minBudget;
    private Double maxBudget;
    
    
    // Tutor specific fields
    private String teachingSubjects;
    private Double hourlyRate;
    private List<TimeSlotResponse> teachingAvailability;
    
    // Read-only fields
    private String createdAt;
    private String lastUpdated;
} 