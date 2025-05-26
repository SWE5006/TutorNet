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

public class AvailableSlot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID availableId;
    private Date scheduleStart;
    private Date scheduleEnd;
    private String slotStatus;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutors;

   
}