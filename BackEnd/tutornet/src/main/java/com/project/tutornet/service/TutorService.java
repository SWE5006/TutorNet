package com.project.tutornet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.dto.TutorRequest;
import com.project.tutornet.entity.Tutor;
import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.repository.TutorRepository;
import com.project.tutornet.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Tutor> getTutorsBySubject(String subjectName) {
        return tutorRepository.findByTeachingSubjectsContaining(subjectName);
    }

    public List<Tutor> searchTutorsByName(String name) {
        return tutorRepository.searchTutorsByName(name);
    }

    public List<Tutor> listAllTutors() {
        return tutorRepository.findAll();
    }

    @Transactional
    public Tutor createTutor(TutorRequest request) {
        // Create UserInfoEntity first
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setEmailAddress(request.getEmailAddress());
        userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
        userInfo.setUsername(request.getUsername());
        userInfo.setMobileNumber(request.getMobileNumber());
        userInfo.setRoles("TUTOR");
        userInfo.setCreateDatetime(LocalDateTime.now());
        userInfo.setActiveStatus("ACTIVE");
        
        // Save UserInfoEntity
        userInfo = userInfoRepository.save(userInfo);
        
        // Create Tutor entity
        Tutor tutor = new Tutor();
        tutor.setUserInfo(userInfo);
        tutor.setBio(request.getBio());
        tutor.setEducation(request.getEducation());
        tutor.setExperience(request.getExperience());
        tutor.setTeachingSubjects(request.getTeachingSubjects());
        tutor.setHourlyRate(request.getHourlyRate());
        
        try {
            return tutorRepository.save(tutor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmailAddress());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create tutor", e);
        }
    }
}
