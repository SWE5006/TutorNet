package com.project.tutornet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TutorRequest extends UserRequest {
    @NotNull
    private String qualification;
 
    @Min(0)
    private int experienceYears;
    private Double hourlyRate;
    private String location;
    private String description;

       public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
       public String getQualification() {
        return qualification;
    }
    public int getExperienceYears() {
        return experienceYears;
    }
    public Double getHourlyRate() {
        return hourlyRate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
   
   
}