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

public class Dispute {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID disputeId;
    private String bookingId;
    private String claimuserId;
    private String adminId;
    private Date disputeDate;
    private String disputeStatus;
    @Column(columnDefinition = "TEXT")
    private String disputeDescription;
    @Column(columnDefinition = "TEXT")
    private String resolutionNotes;
}