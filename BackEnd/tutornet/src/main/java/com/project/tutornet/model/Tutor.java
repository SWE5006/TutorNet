package com.project.tutornet.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tutorId;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private String userId;
    private String availableId;
    private BigDecimal hourlyRate;
    private String qualification;
    private int experienceYears;
    private boolean isActive;
}
