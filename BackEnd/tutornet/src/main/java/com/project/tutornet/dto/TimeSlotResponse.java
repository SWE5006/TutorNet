package com.project.tutornet.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TimeSlotResponse {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String status; // "AVAILABLE", "BOOKED", "CANCELLED"
} 