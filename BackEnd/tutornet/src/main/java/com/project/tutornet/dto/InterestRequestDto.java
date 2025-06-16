package com.project.tutornet.dto;

import java.util.List;
import java.util.UUID;

public class InterestRequestDto {
    private String userId;
    private UUID subjectId;
    private List<TimeSlotDto> availableTimeSlots;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public UUID getSubjectId() { return subjectId; }
    public void setSubjectId(UUID subjectId) { this.subjectId = subjectId; }
    public List<TimeSlotDto> getAvailableTimeSlots() { return availableTimeSlots; }
    public void setAvailableTimeSlots(List<TimeSlotDto> availableTimeSlots) { this.availableTimeSlots = availableTimeSlots; }
} 