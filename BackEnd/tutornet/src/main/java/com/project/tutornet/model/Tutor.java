package com.project.tutornet.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
  
    @OneToMany(mappedBy = "tutors")
   private Set<Subject> subjects;

    @OneToMany(mappedBy = "tutors")
    private List<AvailableSlot> availableSlots;

}
