package com.project.tutornet.model;


import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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

public class Student {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID studentId;
private int age;
private String classLevel;

@OneToOne
@JoinColumn(name = "student_id", referencedColumnName = "user_id")
@MapsId
private User user;

}