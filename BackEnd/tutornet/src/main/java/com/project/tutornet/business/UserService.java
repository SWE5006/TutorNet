package com.project.tutornet.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tutornet.dto.TutorRequest;
import com.project.tutornet.model.Tutor;
import com.project.tutornet.model.User;
import com.project.tutornet.repository.TutorRepository;
import com.project.tutornet.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TutorRepository tutorRepository;


    @Transactional
    public User createTutor(TutorRequest request) {
        
       
        Tutor tutor = new Tutor();

        tutor.setEmail(request.getEmail());
        tutor.setPassword(passwordEncoder().encode(request.getPassword()));
        tutor.setFirstName(request.getFirstName());
        tutor.setLastName(request.getLastName());
        tutor.setUserRole("TUTOR");

        tutor.setQualification(request.getQualification());
        tutor.setExperienceYears(request.getExperienceYears());
        tutor.setHourlyRate(request.getHourlyRate());
        tutor.setActive(true);
       
        try {
            return tutorRepository.save(tutor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create tutor", e);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}