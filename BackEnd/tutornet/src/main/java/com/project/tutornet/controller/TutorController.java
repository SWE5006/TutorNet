package com.project.tutornet.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.SubjectRequest;
import com.project.tutornet.dto.TimeSlotRequest;
import com.project.tutornet.dto.TutorResponse;
import com.project.tutornet.entity.Subject;
import com.project.tutornet.entity.TimeSlot;
import com.project.tutornet.entity.Tutor;
import com.project.tutornet.repository.SubjectRepository;
import com.project.tutornet.repository.TutorRepository;
import com.project.tutornet.service.TutorService;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/by-subject")
    public ResponseEntity<List<TutorResponse>> getTutorsBySubject(@RequestParam String subject) {
        return ResponseEntity.ok(tutorService.getTutorsBySubject(subject));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TutorResponse>> getAllTutors() {
        return ResponseEntity.ok(tutorService.getAllTutors());
    }

     @PostMapping("/{tutorid}/subjects")
    public ResponseEntity<?> addSubject(@PathVariable UUID id, @RequestBody SubjectRequest dto) {
        Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));

        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setTutor(tutor);
        subjectRepository.save(subject);

        return ResponseEntity.ok("Subject added");
    }

    @PostMapping("/{tutorid}/timeslots")
    public ResponseEntity<?> addTimeSlot(@PathVariable("tutorid") UUID tutorId, 
                                       @RequestBody TimeSlotRequest request) {
        try {
            TimeSlot timeSlot = tutorService.addTimeSlot(tutorId, request);
            return ResponseEntity.ok(timeSlot);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Failed to add time slot: " + e.getMessage());
        }
    }
 
    //View Tutor Detail
    //FindTutorByName
    @GetMapping("/by-name")
    public ResponseEntity<List<Tutor>> searchTutorsByName(@RequestParam String name) {
        List<Tutor> result = tutorService.searchTutorsByName(name);
        return ResponseEntity.ok(result);
    }

    

}
