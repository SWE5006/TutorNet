package com.project.tutornet.dto;

public class LoginResponse {
    private String email;
   

    private String firstName;
    private String lastName;
    private String userRole;
    
    public LoginResponse(String email, String firstName, String lastName, String userRole) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
    }
    // Getters and setters
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserRole() {
        return userRole;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}