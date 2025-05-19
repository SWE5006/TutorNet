package com.project.tutornet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.business.UserService;
import com.project.tutornet.dto.ErrorResponse;
import com.project.tutornet.dto.LoginRequest;
import com.project.tutornet.dto.LoginResponse;
import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.dto.TutorRequest;
import com.project.tutornet.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

  

    @GetMapping
    public String sayHello() {
        return "This is from User!";
    }


    @PostMapping("/tutors")
    public ResponseEntity<User> createTutor(@RequestBody TutorRequest request) {
        User user = userService.createTutor(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/students")
    public ResponseEntity<User> createStudent(@RequestBody StudentRequest request) {
        User user = userService.createStudent(request);
        return ResponseEntity.ok(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.findByEmail(loginRequest.getEmail());
           
            if (!passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid password"));
            }

            LoginResponse response = new LoginResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserRole()
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(e.getMessage()));
        }

    }
}
