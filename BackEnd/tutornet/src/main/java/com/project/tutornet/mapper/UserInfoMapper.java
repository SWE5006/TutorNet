package com.project.tutornet.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.tutornet.dto.CreateUserRequest;
import com.project.tutornet.dto.UserRegistrationRequest;
import com.project.tutornet.dto.UserResponse;
import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.enums.UserRoles;
import com.project.tutornet.enums.UserStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserInfoMapper {

  private final PasswordEncoder passwordEncoder;

  public UserInfoEntity mapUserRegistrationToUserInfoEntity(
      UserRegistrationRequest userRegistrationRequest) {
    UserInfoEntity userInfoEntity = new UserInfoEntity();
    userInfoEntity.setId(UUID.randomUUID());
    userInfoEntity.setUsername(userRegistrationRequest.getUserName());
    userInfoEntity.setEmailAddress(userRegistrationRequest.getUserEmail());
    userInfoEntity.setMobileNumber(userRegistrationRequest.getUserMobileNo());
    userInfoEntity.setRoles(userRegistrationRequest.getUserRole());
    userInfoEntity.setCreateDatetime(LocalDateTime.now());
    userInfoEntity.setActiveStatus("ACTIVE");
    userInfoEntity.setPassword(
        passwordEncoder.encode(userRegistrationRequest.getUserPassword()));
    return userInfoEntity;
  }

  public UserResponse convertToUserResponse(UserInfoEntity userInfo) {
    return UserResponse
        .builder()
        .userId(userInfo.getId())
        .userName(userInfo.getUsername())
        .mobileNumber(userInfo.getMobileNumber())
        .activeStatus(userInfo.getActiveStatus())
        .roles(userInfo.getRoles())
        .emailAddress(userInfo.getEmailAddress())
        .createDatetime(userInfo.getCreateDatetime())
        .build();
  }

  public UserInfoEntity mapUserRequestToUserInfoEntity(
      CreateUserRequest userRequest) {
    UserInfoEntity userInfoEntity = new UserInfoEntity();
    userInfoEntity.setId(UUID.randomUUID());
    userInfoEntity.setUsername(userRequest.getUserName());
    userInfoEntity.setEmailAddress(userRequest.getEmailAddress());
    userInfoEntity.setMobileNumber(userRequest.getMobileNumber());
    userInfoEntity.setRoles(UserRoles.STUDENT.name());
    userInfoEntity.setCreateDatetime(LocalDateTime.now());
    userInfoEntity.setActiveStatus("ACTIVE");
    userInfoEntity.setPassword(null);
    return userInfoEntity;
  }

  public UserInfoEntity mapGoogleUserToUserInfoEntity(
      String email,
      String name) {
    UserInfoEntity userInfoEntity = new UserInfoEntity();
    userInfoEntity.setId(UUID.randomUUID());
    userInfoEntity.setUsername(name);
    userInfoEntity.setEmailAddress(email);
    userInfoEntity.setMobileNumber(null);
    userInfoEntity.setRoles(UserRoles.STUDENT.name());
    userInfoEntity.setCreateDatetime(LocalDateTime.now());
    userInfoEntity.setActiveStatus(UserStatus.ACTIVE.name());
    userInfoEntity.setPassword(null);
    return userInfoEntity;
  }
}
