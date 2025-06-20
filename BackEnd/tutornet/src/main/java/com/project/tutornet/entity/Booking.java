package com.project.tutornet.entity;


import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID bookingId;
    
  
    private Date bookingDate;
    private String bookingStatus;
    private String subjectName;
    private int numberOfBooking;

   
    @OneToOne
    @JoinColumn(name = "slot_id")
    private TimeSlot slot;

    private String studentName;
    private String studentId;
}