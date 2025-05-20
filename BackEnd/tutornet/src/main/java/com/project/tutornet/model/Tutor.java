package com.project.tutornet.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Tutor extends User{
 
    private Double hourlyRate;
    private String qualification;
    private int experienceYears;
    private boolean isActive;

}
