package com.project.tutornet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.business.TutorService;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    
    //View Tutor Detail
    //FindTutorById

}
