package com.project.tutornet.dto;

public class SubjectRequest {
    private String subjectCode;
    private String name;
    
    // Constructor
    public SubjectRequest(String subjectCode, String name) {
        this.subjectCode = subjectCode;
        this.name = name;
    }
    
    // Getters and Setters
    public String getSubjectCode() {
        return subjectCode;
    }
    
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}