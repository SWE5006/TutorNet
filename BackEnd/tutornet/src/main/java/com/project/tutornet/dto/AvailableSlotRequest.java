package com.project.tutornet.dto;

import com.project.tutornet.entity.Tutor;

import java.util.Date;


public class AvailableSlotRequest {
   
   
    private Date scheduleStart;
    private Date scheduleEnd;
    private String slotStatus;
    private Tutor tutor;
   

    public void setScheduleStart(Date scheduleStart) {
        this.scheduleStart = scheduleStart;
    }
    public void setScheduleEnd(Date scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }
    public void setSlotStatus(String slotStatus) {
        this.slotStatus = slotStatus;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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
    public Tutor getTutor() {
        return tutor;
    }
   
    
}