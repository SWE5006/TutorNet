package com.project.tutornet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public abstract class UserRequest {
@NotNull @Email
    private String email;
    @NotNull @Size(min = 8)
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

   
    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}

public class TutorRequest extends UserRequest {
    @NotNull
    private String qualification;
    @Min(0)
    private int experienceYears;
    @NotNull @DecimalMin("0.0")
    private BigDecimal hourlyRate;
    private String availabilityId;

    // Getters and setters
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public String getAvailabilityId() { return availabilityId; }
    public void setAvailabilityId(String availabilityId) { this.availabilityId = availabilityId; }
}

public class StudentRequest extends UserRequest {
    @Min(0)
    private int age;
    @NotNull
    private String classLevel;

    // Getters and setters
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getClassLevel() { return classLevel; }
    public void setClassLevel(String classLevel) { this.classLevel = classLevel; }
}