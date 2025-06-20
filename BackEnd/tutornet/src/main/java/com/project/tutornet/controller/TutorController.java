package com.project.tutornet.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.project.tutornet.dto.*;
import com.project.tutornet.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.project.tutornet.entity.Subject;
import com.project.tutornet.entity.TimeSlot;
import com.project.tutornet.entity.Tutor;
import com.project.tutornet.repository.SubjectRepository;
import com.project.tutornet.repository.TutorRepository;
import com.project.tutornet.service.TutorService;

@RestController
@RequestMapping("/api/tutors")
@Slf4j
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private BookingService bookingService;

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

    @PostMapping("/timeslots")
    @PreAuthorize("hasAuthority('SCOPE_EVENT')")
    public ResponseEntity<?> addTimeSlot(Authentication authentication,
                                         @RequestBody TimeSlotRequest request) {
        try {
            // get userid from token
            Jwt jwt = (Jwt) authentication.getCredentials();
            String userid = (String) jwt.getClaims().get("userid");
            UUID tutorid = UUID.fromString(userid);
            TimeSlot timeSlot = tutorService.addTimeSlot(tutorid, request);
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

    @GetMapping("/timeslots/by-email/{email}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<?> getTimeSlotsByTutorEmail(@PathVariable String email) {
        try {
            List<TimeSlot> timeSlots = tutorService.getTimeSlotsByTutorEmail(email);
            return ResponseEntity.ok(timeSlots);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Failed to get time slots: " + e.getMessage());
        }
    }

    @GetMapping("/timeslots/id")
    public ResponseEntity<?> getTimeSlotsByUserId(Authentication authentication) {
        try {
            // get userid from token
            Jwt jwt = (Jwt) authentication.getCredentials();
            String userid = (String) jwt.getClaims().get("userid");
            UUID tutorId = UUID.fromString(userid);
            List<TimeSlotResponse> timeSlots = tutorService.getTimeSlotsByUserId(tutorId)
                .stream()
                .map(slot -> {
                    TimeSlotResponse response = new TimeSlotResponse();
                    response.setId(slot.getId());
                    response.setDayOfWeek(slot.getDayOfWeek());
                    response.setStartTime(slot.getStartTime());
                    response.setEndTime(slot.getEndTime());
                    response.setStatus(slot.getStatus());
                    return response;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(timeSlots);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Failed to get time slots: " + e.getMessage());
        }
    }

    @GetMapping("/timeslots/by-id/{id}")
    public ResponseEntity<?> getTimeSlotsByTutorId(@PathVariable("id") UUID tutorId) {
        try {
            List<TimeSlotResponse> timeSlots = tutorService.getTimeSlotsByTutorId(tutorId)
                .stream()
                .map(slot -> {
                    TimeSlotResponse response = new TimeSlotResponse();
                    response.setId(slot.getId());
                    response.setDayOfWeek(slot.getDayOfWeek());
                    response.setStartTime(slot.getStartTime());
                    response.setEndTime(slot.getEndTime());
                    response.setStatus(slot.getStatus());
                    return response;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(timeSlots);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Failed to get time slots: " + e.getMessage());
        }
    }

//    @PreAuthorize("hasAuthority('SCOPE_EVENT')")
    @DeleteMapping(path = "/timeslots/{timeslotId}/delete")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> deleteTimeSlot(Authentication authentication, @PathVariable @Valid @NotNull String timeslotId) {
        log.info("[TutorController:deleteTimeSlot]Request to delete timeslot started for user: {}", authentication.getName());
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    tutorService.deleteTimeSlot(timeslotId),
                    HttpStatus.OK.value(),
                    null));
        } catch (Exception e) {
            log.error("[TutorController:deleteTimeSlot] Failed to delete timeslot", e);
            ApiResponse<String> response = new ApiResponse<>("Failed to delete timeslot.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path= "/{email}/booking")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByEmail(@PathVariable("email") String email) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByEmail(email);
        return ResponseEntity.ok(bookings);
    }
}
