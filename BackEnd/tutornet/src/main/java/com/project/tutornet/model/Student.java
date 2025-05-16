package com.project.tutornet.model;


import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Student {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID studentId;

@Id
@OneToOne
@JoinColumn(name = "userId") // Foreign key column in Student table
private User user;

private int age;
private String classLevel;
}