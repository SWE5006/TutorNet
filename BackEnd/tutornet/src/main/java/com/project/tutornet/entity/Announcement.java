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