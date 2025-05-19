package com.project.tutornet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.business.TutorService;
import com.project.tutornet.dto.ErrorResponse;
import com.project.tutornet.model.Tutor;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    //List Tutor by Subject Name
    @GetMapping("/subsearch")
    public ResponseEntity<?> searchTutorsBySubject(
            @RequestParam(required = false) String subjectName) {
        try {
            List<Tutor> tutors;
           if (subjectName != null && !subjectName.isEmpty()) {
                tutors = (List<Tutor>) tutorService.findTutorsBySubjectName(subjectName);
            } else {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Please provide subjectName"));
            }

            if (tutors.isEmpty()) {
                return ResponseEntity.ok(new ErrorResponse("No tutors found for the specified subject"));
            }

            return ResponseEntity.ok(tutors);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse("Error searching tutors: " + e.getMessage()));
        }
    }

    //View Tutor Detail
    //FindTutorById

}
