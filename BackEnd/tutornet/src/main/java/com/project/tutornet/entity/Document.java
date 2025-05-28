package com.project.tutornet.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Document {

 @Id
@GeneratedValue(strategy= GenerationType.AUTO)
private UUID documentId ;
@Column(columnDefinition = "TEXT")
private String comment;
private String filePath;
}