package com.project.tutornet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.dto.TutorRequest;
import com.project.tutornet.dto.TutorResponse;
import com.project.tutornet.entity.Subject;
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

    // public List<Tutor> getTutorsBySubject(String subjectName) {
    //     return tutorRepository.findTutorsBySubjectName(subjectName);
    // }

    public List<Tutor> searchTutorsByName(String name) {
        return tutorRepository.searchTutorsByName(name);
    }

    public List<Tutor> listAllTutors() {
        return tutorRepository.findAll();
    }

    @Transactional
    public Tutor createTutor(TutorRequest request) {

    if (userInfoRepository.findByEmailAddress(request.getEmailAddress()).isPresent()) {
        throw new IllegalArgumentException("Email already exists: " + request.getEmailAddress());
    }
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
       // tutor.setTeachingSubjects(request.getTeachingSubjects());
      
        tutor.setHourlyRate(request.getHourlyRate());
        List<Subject> subjectList = new ArrayList<>();
        for (String subjectname : request.getTeachingSubjects()) {
            Subject subject = new Subject();
            subject.setName(subjectname);
            subject.setTutor(tutor);
            subjectList.add(subject);
        }
        tutor.setSubjects(subjectList);
       
        try {
            return tutorRepository.save(tutor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmailAddress());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create tutor", e);
        }

       
    }

    public List<TutorResponse> getTutorsBySubject(String subject) {
    return tutorRepository.findTutorsBySubjectName(subject)
        .stream()
        .map(TutorResponse::new)
        .collect(Collectors.toList());
}

public List<TutorResponse> getAllTutors() {
    return tutorRepository.findAll()
        .stream()
        .map(TutorResponse::new)
        .collect(Collectors.toList());
}
}
