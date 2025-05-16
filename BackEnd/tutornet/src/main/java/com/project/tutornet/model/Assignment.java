package com.project.tutornet.model;


import java.util.Date;

import jakarta.persistence.Column;
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
public class Assignment {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
private String assignmentId;
private String title;
@Column(columnDefinition = "TEXT")
private String assignmentDescription;
private Date dueDate;
private String sessionId;
}