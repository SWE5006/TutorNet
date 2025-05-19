package com.project.tutornet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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