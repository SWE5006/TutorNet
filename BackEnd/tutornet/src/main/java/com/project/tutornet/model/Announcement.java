package com.project.tutornet.model;


import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

public class Announcement {

@Id
@GeneratedValue(strategy= GenerationType.AUTO)
private UUID announcementId;
private String title;
@Column(columnDefinition = "TEXT")
private String announcementText;
private Date announceDate;
private String sessionId;
}