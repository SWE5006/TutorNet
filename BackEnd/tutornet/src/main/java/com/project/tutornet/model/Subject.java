package com.project.tutornet.model;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Subject {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID subjectId;
    private String subjectCode;
    @Column(columnDefinition = "TEXT")
    private String name;
    
   @ManyToOne
   @JoinColumn(name = "tutor_id")
   private Tutor tutors;
}