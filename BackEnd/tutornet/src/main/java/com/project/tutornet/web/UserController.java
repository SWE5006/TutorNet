package com.project.tutornet.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.business.UserService;
import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.dto.TutorRequest;
import com.project.tutornet.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
private final UserService userService;

    @GetMapping
    public String sayHello() {
        return "This is from User!";
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/tutors")
    public ResponseEntity<User> createTutor(@Valid @RequestBody TutorRequest request) {
        User user = userService.createTutor(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/students")
    public ResponseEntity<User> createStudent(@Valid @RequestBody StudentRequest request) {
        User user = userService.createStudent(request);
        return ResponseEntity.ok(user);
    }
}
