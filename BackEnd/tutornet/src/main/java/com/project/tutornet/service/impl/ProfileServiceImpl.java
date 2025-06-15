package com.project.tutornet.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.dto.ProfileRequest;
import com.project.tutornet.dto.ProfileResponse;
import com.project.tutornet.dto.TimeSlotRequest;
import com.project.tutornet.dto.TimeSlotResponse;
import com.project.tutornet.entity.Student;
import com.project.tutornet.entity.TimeSlot;
import com.project.tutornet.entity.Tutor;
import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.repository.StudentRepository;
import com.project.tutornet.repository.TutorRepository;
import com.project.tutornet.repository.UserRepository;
import com.project.tutornet.service.ProfileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public ProfileResponse getProfileByEmail(String emailaddress) {
        log.info("[ProfileServiceImpl:getProfileByEmail] Getting profile for email: {}", emailaddress);
        
        return getProfile(emailaddress);
    }

    public ProfileResponse getProfile(String email_address) {
        
        Optional<UserInfoEntity> retrieveuser = userRepository.findByEmailAddress(email_address);
           
        UserInfoEntity user= retrieveuser.get();
        if (user == null) {
            log.error("[ProfileServiceImpl:getProfile] User not found for email: {}", email_address);
            throw new RuntimeException("User not found");
        }
        log.info("User Role is:" + user.getRoles());
        try {
            switch (user.getRoles()) {
                case "STUDENT":
                    Student student = studentRepository.findByUserInfoId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Student profile not found"));
                    return ProfileResponse.builder()
                        .userId(user.getId())
                        .email(user.getEmailAddress())
                        .fullName(user.getUsername())
                        .role(user.getRoles())
                        .bio(student.getBio())
                        .education(student.getEducation())
                        .experience(student.getExperience())
                        .build();
                    
                case "TUTOR":
                    Tutor tutor = tutorRepository.findByUserInfoId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Tutor profile not found"));
                    return ProfileResponse.builder()
                        .userId(user.getId())
                        .email(user.getEmailAddress())
                        .fullName(user.getUsername())
                        .role(user.getRoles())
                        .bio(tutor.getBio())
                        .education(tutor.getEducation())
                        .experience(tutor.getExperience())
                        .hourlyRate(tutor.getHourlyRate())
                        .teachingAvailability(convertToTimeSlotResponses(tutor.getTeachingAvailability()))
                        .teachingSubjects(
                            tutor.getSubjects() != null 
                                ? tutor.getSubjects().toString() 
                                : null)
                        .build();
                    
                default:
                    return ProfileResponse.builder()
                        .userId(user.getId())
                        .email(user.getEmailAddress())
                        .fullName(user.getUsername())
                        .role(user.getRoles())
                        .build();
            }
        } catch (RuntimeException e) {
            log.error("[ProfileServiceImpl:getProfile] Error getting profile: {}", e.getMessage());
            throw new RuntimeException("Error getting profile: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(UUID userId, ProfileRequest request) {
        log.info("[ProfileServiceImpl:updateProfile] Updating profile for user: {}", userId);
        UserInfoEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if ("STUDENT".equals(user.getRoles())) {
            return updateStudentProfile(request);
        } else if ("TUTOR".equals(user.getRoles())) {
            return updateTutorProfile(request);
        }

        throw new RuntimeException("Invalid user role");
    }

    @Override
    public ProfileResponse getStudentProfile(UUID userId) {
        log.info("[ProfileServiceImpl:getStudentProfile] Getting student profile for user: {}", userId);
        UserInfoEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Student student = studentRepository.findByUserInfoId(userId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        return ProfileResponse.builder()
            .userId(user.getId())
            .email(user.getEmailAddress())
            .fullName(user.getUsername())
            .role("STUDENT")
            .bio(student.getBio())
            .education(student.getEducation())
            .experience(student.getExperience())
            .interestedSubjects(student.getInterestedSubjects())
            .topics(student.getTopics())
            .minBudget(student.getMinBudget())
            .maxBudget(student.getMaxBudget())
            .createdAt(student.getCreatedAt().toString())
            .lastUpdated(student.getUpdatedAt().toString())
            .build();
    }

    @Override
    public ProfileResponse getTutorProfile(UUID userId) {
        log.info("[ProfileServiceImpl:getTutorProfile] Getting tutor profile for user: {}", userId);
        UserInfoEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Tutor tutor = tutorRepository.findByUserInfoId(userId)
            .orElseThrow(() -> new RuntimeException("Tutor not found"));

        return ProfileResponse.builder()
            .userId(user.getId())
            .email(user.getEmailAddress())
            .fullName(user.getUsername())
            .role("TUTOR")
            .bio(tutor.getBio())
            .education(tutor.getEducation())
            .experience(tutor.getExperience())
           // .teachingSubjects(tutor.getTeachingSubjects())
            .hourlyRate(tutor.getHourlyRate())
            .teachingAvailability(convertToTimeSlotResponses(tutor.getTeachingAvailability()))
            .createdAt(tutor.getCreatedAt().toString())
            .lastUpdated(tutor.getUpdatedAt().toString())
            .build();
    }

    @Override
    @Transactional
    public ProfileResponse updateStudentProfile(ProfileRequest request) {
        log.info("[ProfileServiceImpl:updateStudentProfile] Updating student profile for user: {}", request.getUserId());
        UserInfoEntity user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Student student = studentRepository.findByUserInfoId(request.getUserId())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        // Update editable fields
        student.setBio(request.getBio());
        student.setEducation(request.getEducation());
        student.setExperience(request.getExperience());
        student.setInterestedSubjects(request.getInterestedSubjects());
        student.setTopics(request.getTopics());
        student.setMinBudget(request.getMinBudget());
        student.setMaxBudget(request.getMaxBudget());
        student.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(student);
        return getStudentProfile(user.getId());
    }

    @Override
    @Transactional
    public ProfileResponse updateTutorProfile(ProfileRequest request) {
        log.info("[ProfileServiceImpl:updateTutorProfile] Updating tutor profile for user: {}", request.getUserId());
        UserInfoEntity user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Tutor tutor = tutorRepository.findByUserInfoId(request.getUserId())
            .orElseThrow(() -> new RuntimeException("Tutor not found"));

        // Update editable fields
        tutor.setBio(request.getBio());
        tutor.setEducation(request.getEducation());
        tutor.setExperience(request.getExperience());
        tutor.setHourlyRate(request.getHourlyRate());

        // Clear existing time slots
        if (tutor.getTeachingAvailability() != null) {
            tutor.getTeachingAvailability().clear();
        }

        // Add new time slots
        if (request.getTeachingAvailability() != null) {
            List<TimeSlot> slots = convertToTimeSlots(request.getTeachingAvailability());
            for (TimeSlot slot : slots) {
                slot.setTutor(tutor);
                tutor.getTeachingAvailability().add(slot);
            }
        }

        tutor.setUpdatedAt(LocalDateTime.now());
        tutorRepository.save(tutor);
        return getTutorProfile(user.getId());
    }

    private List<TimeSlotResponse> convertToTimeSlotResponses(List<TimeSlot> timeSlots) {
        if (timeSlots == null) {
            return List.of();
        }
        return timeSlots.stream()
            .map(slot -> TimeSlotResponse.builder()
                .dayOfWeek(slot.getDayOfWeek())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(slot.getStatus())
                .build())
            .collect(Collectors.toList());
    }

    private List<TimeSlot> convertToTimeSlots(List<TimeSlotRequest> requests) {
        if (requests == null) {
            return List.of();
        }
        return requests.stream()
            .map(request -> {
                TimeSlot slot = new TimeSlot();
                slot.setDayOfWeek(request.getDayOfWeek());
                slot.setStartTime(request.getStartTime());
                slot.setEndTime(request.getEndTime());
                slot.setStatus("AVAILABLE");
                return slot;
            })
            .collect(Collectors.toList());
    }

    @Override
    public Object getAvailability(UUID userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object updateAvailability(UUID userId, TimeSlotRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getProfileByUserName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProfileResponse getProfile(UUID userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}