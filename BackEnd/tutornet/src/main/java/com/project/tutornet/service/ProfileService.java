package com.project.tutornet.service;

import java.util.UUID;

import com.project.tutornet.dto.ProfileRequest;
import com.project.tutornet.dto.ProfileResponse;

public interface ProfileService {
    ProfileResponse getProfile(UUID userId);
    ProfileResponse getProfileByUsername(String username);
    ProfileResponse updateProfile(ProfileRequest request);
    ProfileResponse getStudentProfile(UUID studentId);
    ProfileResponse getTutorProfile(UUID tutorId);
    ProfileResponse updateStudentProfile(ProfileRequest request);
    ProfileResponse updateTutorProfile(ProfileRequest request);
} 