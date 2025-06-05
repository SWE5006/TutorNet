package com.project.tutornet.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.project.tutornet.dto.ProfileRequest;
import com.project.tutornet.dto.ProfileResponse;
import com.project.tutornet.dto.TimeSlotRequest;
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

    @GetMapping("/current")
    public ResponseEntity<ProfileResponse> getCurrentProfile(Authentication authentication) {
        log.info("[ProfileController:getCurrentProfile] Getting profile for current user");
        return ResponseEntity.ok(profileService.getProfileByEmail(authentication.getName()));
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

    @GetMapping("/{userId}/availability")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<?> getAvailability(@PathVariable UUID userId) {
        log.info("[ProfileController:getAvailability] Getting availability for user: {}", userId);
        return ResponseEntity.ok(profileService.getAvailability(userId));
    }

    @PutMapping("/{userId}/availability")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<?> updateAvailability(
            @PathVariable UUID userId,
            @RequestBody TimeSlotRequest request) {
        log.info("[ProfileController:updateAvailability] Updating availability for user: {}", userId);
        return ResponseEntity.ok(profileService.updateAvailability(userId, request));
    }
} 