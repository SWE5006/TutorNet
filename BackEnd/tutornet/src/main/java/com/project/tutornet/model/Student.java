package com.project.tutornet.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@GeneratedValue(strategy = GenerationType.UUID)
private String studentId;

@ManyToOne
@JoinColumn(name = "userId", insertable = false, updatable = false)
private String userId;
private int age;
private String classLevel;
}