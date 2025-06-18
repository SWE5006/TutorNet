package com.project.tutornet.dto;


public class CreateBookingRequest {
    private String studentEmail;
   
    
    private String subjectName;
    private String[] slotId;
    private int numberOfBooking;
    
    public String getStudentEmail() {
        return studentEmail;
    }
    
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public void setSlotId(String[] slotId) {
        this.slotId = slotId;
    }
    public void setNumberOfBooking(int numberOfBooking) {
        this.numberOfBooking = numberOfBooking;
    }
   
    public String getSubjectName() {
        return subjectName;
    }
    public String[] getSlotId() {
        return slotId;
    }
    public int getNumberOfBooking() {
        return numberOfBooking;
    }

}