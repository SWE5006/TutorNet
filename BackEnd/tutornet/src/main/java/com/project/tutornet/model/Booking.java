package com.project.tutornet.model;


import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID bookingId;
    
  
    private Date bookingDate;
    private Date scheduleStart;
    private Date scheduleEnd;
    private String bookingStatus;
    private String subjectName;

   
    @OneToOne
    @JoinColumn(name = "slot_id")
    private AvailableSlot slot;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}