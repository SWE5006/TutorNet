package com.project.tutornet.dto;

import java.util.List;
import java.util.UUID;

import com.project.tutornet.component.EncryptAttributeConverter;

import jakarta.persistence.Convert;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TutorRequest {
   @NotNull(message = "User ID is required")
    private UUID userId;
    
    @NotBlank(message = "Bio is required")
    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    private String bio;
    
    @Size(max = 500, message = "Education cannot exceed 500 characters")
    private String education;
    
    @Size(max = 1000, message = "Experience cannot exceed 1000 characters")
    private String experience;
    
    private List<String> teachingSubjects;
    
    @NotNull(message = "Hourly rate is required")
    @Positive(message = "Hourly rate must be positive")
    private Double hourlyRate;
    
    private String location;
    
  
    
    // Constructors
    public TutorRequest() {}
    
    // Getters and Setters
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    public List<String> getTeachingSubjects() { return teachingSubjects; }
    public void setTeachingSubjects(List<String> teachingSubjects) { this.teachingSubjects = teachingSubjects; }
    
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
 
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

   
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public String getEmailAddress() {
    return emailAddress;
  }
  
  public String getMobileNumber() {
    return mobileNumber;
  }
 
  private String username;
	private String password;
	@Convert(converter = EncryptAttributeConverter.class)
	private String emailAddress;

	@Convert(converter = EncryptAttributeConverter.class)
	private String mobileNumber;
}