package com.project.tutornet.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutionSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;
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