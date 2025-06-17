package com.project.tutornet.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.entity.Student;
import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.repository.StudentRepository;
import com.project.tutornet.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Student createStudent(StudentRequest request) {
        // Create UserInfoEntity first
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setEmailAddress(request.getEmailAddress());
        userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
        userInfo.setUsername(request.getUsername());
        userInfo.setMobileNumber(request.getMobileNumber());
        userInfo.setRoles("STUDENT");
        userInfo.setCreateDatetime(LocalDateTime.now());
        userInfo.setActiveStatus("ACTIVE");
        
        // Save UserInfoEntity
        userInfo = userInfoRepository.save(userInfo);
        
        // Create Student entity
        Student student = new Student();
        student.setUserInfo(userInfo);
        student.setBio(request.getBio());
        student.setEducation(request.getEducation());
        student.setExperience(request.getExperience());
        student.setInterestedSubjects(request.getInterestedSubjects());
        student.setTopics(request.getTopics());
        student.setMinBudget(request.getMinBudget());
        student.setMaxBudget(request.getMaxBudget());
        
        // Save Student entity
        return studentRepository.save(student);
    }
}
