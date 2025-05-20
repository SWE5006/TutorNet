package com.project.tutornet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TutorRequest extends UserRequest {
    @NotNull
    private String qualification;
    @Min(0)
    private int experienceYears;
    private Double hourlyRate;
    // private Set<Subject> subjects;
   
    // private List<AvailableSlot> availableSlots;
    
    // Getters and setters
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    // public Set<Subject> getSubjects() {
    //     return subjects;
    // }
    // public List<AvailableSlot> getAvailableSlots() {
    //     return availableSlots;
    // }
    // public void setSubjects(Set<Subject> subjects) {
    //     this.subjects = subjects;
    // }
    // public void setAvailableSlots(List<AvailableSlot> availableSlots) {
    //     this.availableSlots = availableSlots;
    // }
   
}