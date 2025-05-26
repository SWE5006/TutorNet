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
    private UserInfoEntity student;
}