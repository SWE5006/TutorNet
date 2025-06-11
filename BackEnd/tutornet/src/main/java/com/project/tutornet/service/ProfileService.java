package com.project.tutornet.service;

import java.util.UUID;

import com.project.tutornet.dto.ProfileRequest;
import com.project.tutornet.dto.ProfileResponse;
import com.project.tutornet.dto.TimeSlotRequest;

public interface ProfileService {
    ProfileResponse getProfile(UUID userId);
    ProfileResponse getProfileByEmail(String emailaddress);
    ProfileResponse updateProfile(UUID userId, ProfileRequest request);
    ProfileResponse getStudentProfile(UUID studentId);
    ProfileResponse getTutorProfile(UUID tutorId);
    ProfileResponse updateStudentProfile(ProfileRequest request);
    ProfileResponse updateTutorProfile(ProfileRequest request);
    Object getAvailability(UUID userId);
    Object updateAvailability(UUID userId, TimeSlotRequest request);
    Object getProfileByUserName(String name);
    
} 