package com.project.tutornet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {

     @NotNull @Email
    private String email;
    @NotNull @Size(min = 8)
    private String password;
       // Getters and setters
       public String getEmail() { return email; }
       public void setEmail(String email) { this.email = email; }
       public String getPassword() { return password; }
       public void setPassword(String password) { this.password = password; } 
}
