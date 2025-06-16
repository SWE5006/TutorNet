package com.project.tutornet.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.ProfileRequest;
import com.project.tutornet.dto.ProfileResponse;
import com.project.tutornet.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/current/{email}")
    public ResponseEntity<ProfileResponse> getCurrentProfile(@PathVariable("email") String email) {
        log.info("[ProfileController:getCurrentProfile] Getting profile for email: {}", email);
        return ResponseEntity.ok(profileService.getProfileByEmail(email));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable UUID userId) {
        log.info("[ProfileController:getProfile] Getting profile for user: {}", userId);
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable UUID userId,
            @RequestBody ProfileRequest request) {
        log.info("[ProfileController:updateProfile] Updating profile for user: {}", userId);
        return ResponseEntity.ok(profileService.updateProfile(userId, request));
    }

   
}