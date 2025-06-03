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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TutorService {

 @Autowired
 private TutorRepository tutorRepository;
 private final PasswordEncoder passwordEncoder;
 public List<Tutor> getTutorsBySubject(String subjectName) {
   return tutorRepository.findBySubjects_Name(subjectName);
 }

 public List<Tutor> searchTutorsByName(String name) {
   return tutorRepository.searchTutorsByName(name);
 }

    public List<Tutor> listAllTutors() {
        return tutorRepository.findAll();
    }


    @Transactional
    public UserInfoEntity createTutor(TutorRequest request) {
        
       
        Tutor tutor = new Tutor();

        tutor.setEmailAddress(request.getEmailAddress());
        tutor.setPassword(request.getPassword());
        tutor.setUsername(request.getUsername());
       tutor.setMobileNumber(request.getMobileNumber());
       tutor.setRoles("TUTOR");
      
       

        tutor.setQualification(request.getQualification());
        tutor.setExperienceYears(request.getExperienceYears());
        tutor.setHourlyRate(request.getHourlyRate());
        tutor.setActiveStatus("ACTIVE");
        tutor.setCreateDatetime(LocalDateTime.now());
       

        try {
            return tutorRepository.save(tutor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmailAddress());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create tutor", e);
        }
    }
}
