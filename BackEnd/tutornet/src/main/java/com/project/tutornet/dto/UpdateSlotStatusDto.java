package com.project.tutornet.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateSlotStatusDto {
  @NotBlank(message = "Slot status is required")
    private String slotStatus;
    
    // Constructors
    public UpdateSlotStatusDto() {}
    
    public UpdateSlotStatusDto(String slotStatus) {
        this.slotStatus = slotStatus;
    }
    
    // Getters and Setters
    public String getSlotStatus() { return slotStatus; }
    public void setSlotStatus(String slotStatus) { this.slotStatus = slotStatus; }
}
