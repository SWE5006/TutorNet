package com.project.tutornet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.entity.UserInfoEntity;

@RestController
@RequestMapping("/api/students")
public class StudentController {


   

 @PostMapping("/create")
    public ResponseEntity<UserInfoEntity> createStudent(@RequestBody StudentRequest request) {
        UserInfoEntity user=new UserInfoEntity();
        // = tutorService.createTutor(request);
        return ResponseEntity.ok(user);
    }

   
}
