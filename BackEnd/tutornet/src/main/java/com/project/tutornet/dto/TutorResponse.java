package com.project.tutornet.dto;

import java.util.UUID;
import java.util.stream.Collectors;

import com.project.tutornet.entity.Tutor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorResponse {
    private UUID id;
    private String username;
    private String bio;
    private String education;
    private String experience;
    private Double hourlyRate;
    private String subjects;


    public TutorResponse(Tutor tutor) {
    this.id = tutor.getId();
    this.username = tutor.getUsername();
    this.bio = tutor.getBio();
    this.education = tutor.getEducation();
    this.experience = tutor.getExperience();
    this.hourlyRate = tutor.getHourlyRate();
    // Concatenate subject names
    this.subjects = tutor.getSubjects() != null
        ? tutor.getSubjects().stream()
            .map(s -> s.getName())
            .collect(Collectors.joining(", "))
        : "";
    }

    // All-args constructor or builder if needed

    // Getters and setters for all fields
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getSubjects() { return subjects; }
    public void setSubjects(String subjects) { this.subjects = subjects; }
}