package com.project.tutornet.dto;



import com.project.tutornet.component.EncryptAttributeConverter;

import jakarta.persistence.Convert;

public abstract class UserRequest {

	
	
  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
 
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
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
  
  public String getMobileNumber() {
    return mobileNumber;
  }
 
  private String username;
	private String password;
	@Convert(converter = EncryptAttributeConverter.class)
	private String emailAddress;

	@Convert(converter = EncryptAttributeConverter.class)
	private String mobileNumber;
	

}



