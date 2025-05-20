package com.project.tutornet.model;


import java.util.Date;
import java.util.UUID;

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

public class AvailableSlot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID availableId;
    private Date scheduleStart;
    private Date scheduleEnd;
    private String tutorId;
    private String slotStatus;
}