package com.project.tutornet.dto;

import java.util.UUID;


public class CreateBookingRequest {
    private UUID studentId;
    private String subjectName;
    private UUID slotId;
    private int numberOfBooking;

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }
    public void setNumberOfBooking(int numberOfBooking) {
        this.numberOfBooking = numberOfBooking;
    }
    public UUID getStudentId() {
        return studentId;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public UUID getSlotId() {
        return slotId;
    }
    public int getNumberOfBooking() {
        return numberOfBooking;
    }

}