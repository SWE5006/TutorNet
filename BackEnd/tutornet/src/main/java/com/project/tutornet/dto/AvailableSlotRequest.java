package com.project.tutornet.dto;

import java.util.Date;
import java.util.UUID;

import com.project.tutornet.entity.AvailableSlot;

public class AvailableSlotRequest {

  private UUID availableId;

  private Date scheduleStart;
  private Date scheduleEnd;
  private String slotStatus;
  private UUID tutorId;
  private String tutorName;

  // Constructors
  public AvailableSlotRequest() {}

  public AvailableSlotRequest(AvailableSlot slot) {
    this.availableId = slot.getAvailableId();
    this.scheduleStart = slot.getScheduleStart();
    this.scheduleEnd = slot.getScheduleEnd();
    this.slotStatus = slot.getSlotStatus();
    this.tutorId = slot.getTutors().getId();
    this.tutorName =
      slot.getTutors().getUsername();
  }

  public void setAvailableId(UUID availableId) {
    this.availableId = availableId;
  }

  public UUID getAvailableId() {
    return availableId;
  }

  public void setTutorId(UUID tutorId) {
    this.tutorId = tutorId;
  }

  public void setTutorName(String tutorName) {
    this.tutorName = tutorName;
  }

  public UUID getTutorId() {
    return tutorId;
  }

  public String getTutorName() {
    return tutorName;
  }

  public void setScheduleStart(Date scheduleStart) {
    this.scheduleStart = scheduleStart;
  }

  public void setScheduleEnd(Date scheduleEnd) {
    this.scheduleEnd = scheduleEnd;
  }

  public void setSlotStatus(String slotStatus) {
    this.slotStatus = slotStatus;
  }

  public Date getScheduleStart() {
    return scheduleStart;
  }

  public Date getScheduleEnd() {
    return scheduleEnd;
  }

  public String getSlotStatus() {
    return slotStatus;
  }
}
