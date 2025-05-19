package com.project.tutornet.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
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
    
    @OneToMany(mappedBy="subject",cascade = CascadeType.ALL)
    private List<TutorSubject> tutorSubjects = new ArrayList<>();

    @OneToMany(mappedBy="subject",cascade = CascadeType.ALL)
    private List<StudentSubject> studentSubjects = new ArrayList<>();

}