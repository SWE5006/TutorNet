package com.project.tutornet.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.ProfileRequest;
import com.project.tutornet.dto.ProfileResponse;
import com.project.tutornet.service.ProfileService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/profile")
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/current")
    public ResponseEntity<ProfileResponse> getCurrentProfile(Authentication authentication) {
        log.info("[ProfileController:getCurrentProfile] Getting profile for current user: {}", authentication.getName());
        return ResponseEntity.ok(profileService.getProfileByUsername(authentication.getName()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable UUID userId) {
        log.info("[ProfileController:getProfile] Getting profile for user: {}", userId);
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ProfileResponse> getStudentProfile(@PathVariable UUID studentId) {
        log.info("[ProfileController:getStudentProfile] Getting student profile: {}", studentId);
        return ResponseEntity.ok(profileService.getStudentProfile(studentId));
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<ProfileResponse> getTutorProfile(@PathVariable UUID tutorId) {
        log.info("[ProfileController:getTutorProfile] Getting tutor profile: {}", tutorId);
        return ResponseEntity.ok(profileService.getTutorProfile(tutorId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody ProfileRequest request) {
        log.info("[ProfileController:updateProfile] Updating profile for user: {}", userId);
        request.setUserId(userId);
        return ResponseEntity.ok(profileService.updateProfile(request));
    }
} 