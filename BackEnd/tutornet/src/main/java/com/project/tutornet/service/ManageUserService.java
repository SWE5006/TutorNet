package com.project.tutornet.service;



import java.util.List;
import java.util.Optional;

import com.project.tutornet.dto.CreateUserRequest;
import com.project.tutornet.dto.ManageUserRequest;
import com.project.tutornet.dto.UserResponse;
import com.project.tutornet.entity.UserInfoEntity;

public interface ManageUserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserDetails(String userId);
    Optional<UserInfoEntity> findByEmail(String emailAddress);
    UserResponse createUser(CreateUserRequest userRequest);
    void saveUser(UserInfoEntity userInfo);
    void deleteUser(String userId);
    String updateUser(ManageUserRequest userRequest);



    
}
