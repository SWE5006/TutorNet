package com.project.tutornet.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TimeSlotResponse {
    private UUID id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String status;
}