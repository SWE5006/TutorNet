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

import com.project.tutornet.dto.AvailableSlotRequest;
import com.project.tutornet.dto.SubjectRequest;
import com.project.tutornet.entity.AvailableSlot;
import com.project.tutornet.entity.Subject;
import com.project.tutornet.entity.Tutor;
import com.project.tutornet.repository.AvailableSlotRepository;
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

    @Autowired
    private AvailableSlotRepository slotRepository;

   


    @GetMapping("/by-subject")
    public List<Tutor> getTutorsBySubject(@RequestParam String subject)
    {
        return tutorService.getTutorsBySubject(subject);
    }

    @GetMapping("/all")
    public List<Tutor> getAllTutors()
    {
        return tutorService.listAllTutors();
    }

     @PostMapping("/{tutorid}/subjects")
    public ResponseEntity<?> addSubject(@PathVariable UUID id, @RequestBody SubjectRequest dto) {
        Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));

        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setTutors(tutor);
        subjectRepository.save(subject);

        return ResponseEntity.ok("Subject added");
    }

    @PostMapping("/{tutorid}/slots")
    public ResponseEntity<?> addSlot(@PathVariable UUID id, @RequestBody AvailableSlotRequest dto) {
        Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));

        AvailableSlot slot = new AvailableSlot();
        slot.setScheduleStart(dto.getScheduleStart());
        slot.setScheduleEnd(dto.getScheduleEnd());
        slot.setTutors(tutor);
        slot.setSlotStatus(dto.getSlotStatus());
        slotRepository.save(slot);

        return ResponseEntity.ok("Slot added");
    }

    //View Tutor Detail
    //FindTutorByName
    @GetMapping("/by-name")
    public ResponseEntity<List<Tutor>> searchTutorsByName(@RequestParam String name) {
        List<Tutor> result = tutorService.searchTutorsByName(name);
        return ResponseEntity.ok(result);
    }

}
