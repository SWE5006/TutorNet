package com.project.tutornet.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    private String bookingId;
    private String studentId;
    private String tutorId;
    private Date bookingDate;
    private Date scheduleStart;
    private Date scheduleEnd;
    private String bookingStatus;
}