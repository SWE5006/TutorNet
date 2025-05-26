package com.project.tutornet.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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