package com.project.tutornet.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID ratingId;
    private String sessionId;
    private double rating;
}