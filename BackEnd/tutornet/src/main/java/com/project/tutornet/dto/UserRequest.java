package com.project.tutornet.dto;

import java.time.LocalDateTime;

import com.project.tutornet.component.EncryptAttributeConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Id;

public abstract class UserRequest {
  @Id
	
	
  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
  public void setActiveStatus(String activeStatus) {
    this.activeStatus = activeStatus;
  }
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
  public void setRoles(String roles) {
    this.roles = roles;
  }
  public void setCreateDatetime(LocalDateTime createDatetime) {
    this.createDatetime = createDatetime;
  }
   
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public String getEmailAddress() {
    return emailAddress;
  }
  public String getActiveStatus() {
    return activeStatus;
  }
  public String getMobileNumber() {
    return mobileNumber;
  }
  public String getRoles() {
    return roles;
  }
  public LocalDateTime getCreateDatetime() {
    return createDatetime;
  }
    private String username;
	private String password;
	@Convert(converter = EncryptAttributeConverter.class)
	private String emailAddress;
	private String activeStatus;
	@Convert(converter = EncryptAttributeConverter.class)
	private String mobileNumber;
	private String roles;
	private LocalDateTime createDatetime;
}



