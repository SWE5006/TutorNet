package com.project.tutornet.dto;

import jakarta.validation.constraints.NotBlank;

public class SubjectRequest {
    @NotBlank(message = "Subject code is required")
    private String subjectCode;
    
    @NotBlank(message = "Subject name is required")
    private String name;
    
    public SubjectRequest() {}
    
    public SubjectRequest(String subjectCode, String name) {
        this.subjectCode = subjectCode;
        this.name = name;
    }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}