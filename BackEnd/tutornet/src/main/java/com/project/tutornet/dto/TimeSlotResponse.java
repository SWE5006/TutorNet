package com.project.tutornet.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeSlotResponse {
    private UUID id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String status; // "AVAILABLE", "BOOKED", "CANCELLED"
} 