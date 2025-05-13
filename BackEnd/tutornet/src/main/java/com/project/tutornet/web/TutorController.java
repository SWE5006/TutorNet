package com.project.tutornet.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {

    @GetMapping
    public String sayHello() {
        return "This is from Tutor!";
    }
}

