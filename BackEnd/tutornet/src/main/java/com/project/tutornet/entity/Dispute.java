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