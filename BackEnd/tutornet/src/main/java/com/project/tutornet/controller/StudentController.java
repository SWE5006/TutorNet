package com.project.tutornet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    @Autowired
    private StudentService studentService;

 @PostMapping("/create")
    public ResponseEntity<UserInfoEntity> createStudent(@RequestBody StudentRequest request) {
        UserInfoEntity user=studentService.createStudent(request);
        return ResponseEntity.ok(user);
    }

   
}
