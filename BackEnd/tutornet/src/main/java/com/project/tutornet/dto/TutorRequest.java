package com.project.tutornet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TutorRequest extends UserRequest {
    @NotNull
    private String qualification;
    @Min(0)
    private int experienceYears;
    private Double hourlyRate;
    private String availabilityId;

    // Getters and setters
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    public String getAvailabilityId() { return availabilityId; }
    public void setAvailabilityId(String availabilityId) { this.availabilityId = availabilityId; }
}