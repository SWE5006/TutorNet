package com.project.tutornet.controller;

import com.project.tutornet.dto.InterestRequestDto;
import com.project.tutornet.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interest")
public class InterestController {
    @Autowired
    private InterestService interestService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitInterest(@RequestBody InterestRequestDto dto) {
        interestService.saveInterest(dto);
        return ResponseEntity.ok("{\"result\":\"success\"}");
    }
} 