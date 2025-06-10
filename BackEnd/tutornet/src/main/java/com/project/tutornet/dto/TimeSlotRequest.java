package com.project.tutornet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TimeSlotRequest {
    @NotBlank
    private String dayOfWeek;
    @NotBlank
    private String startTime;
    @NotBlank
    private String endTime;
    private String status;  // e.g., "AVAILABLE", "BOOKED"
} 