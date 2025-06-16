package com.project.tutornet.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileRequest {
    @NotNull
    private UUID userId;
    
    // Common fields
    private String bio;
    private String education;
    private String experience;
    
    // Student specific fields
    private List<String> interestedSubjects;
    private List<String> topics;
    private Double minBudget;
    private Double maxBudget;
    private List<TimeSlotRequest> availability;
    
    // Tutor specific fields
    private List<String> teachingSubjects;
    private Double hourlyRate;
    private List<TimeSlotRequest> teachingAvailability;
} 