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
public class AvailableSlot {
    private String availableId;
    private Date scheduleStart;
    private Date scheduleEnd;
    private String tutorId;
}