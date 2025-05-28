package com.project.tutornet.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.entity.Student;
import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

     @Transactional
    public UserInfoEntity createStudent(StudentRequest request) {
        
       
        Student student = new Student();

        student.setEmailAddress(request.getEmailAddress());
        student.setPassword(request.getPassword());
        student.setUsername(request.getUsername());
       student.setMobileNumber(request.getMobileNumber());
       student.setRoles("STUDENT");
      
       student.setCreateDatetime(LocalDateTime.now());
        student.setAge(request.getAge());
        student.setActiveStatus("ACTIVE");
        student.setClassLevel(request.getClassLevel());
       
       
        
            return studentRepository.save(student);
       
    }

}
