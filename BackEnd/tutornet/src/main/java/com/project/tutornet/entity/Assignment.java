package com.project.tutornet.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Assignment {

@Id
@GeneratedValue(strategy= GenerationType.AUTO)
private UUID assignmentId;
private String title;
@Column(columnDefinition = "TEXT")
private String assignmentDescription;
private Date dueDate;
private String sessionId;
}