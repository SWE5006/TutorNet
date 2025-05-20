package com.project.tutornet.model;


import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TutionSession {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID tsessionId;
    private String academicTerm;
    private int academicYear;
    private Date startTime;
    private Date endTime;
    private String sessionType;
    private String studentId;
    private String tutorId;
    private String subjectId;
    private String tutionLocation;
}