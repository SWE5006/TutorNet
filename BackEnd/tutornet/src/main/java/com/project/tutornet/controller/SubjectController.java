package com.project.tutornet.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.repository.SubjectRepository;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

     @Autowired
    private SubjectRepository subjectRepository;
    
    // Option 3: Return only distinct subject codes
    @GetMapping("/distinct/codes")
    public ResponseEntity<List<String>> getDistinctSubjectCodes() {
        List<String> distinctCodes = subjectRepository.findDistinctSubjectCodes();
        return ResponseEntity.ok(distinctCodes);
    }

        // Option 3: Return only distinct subject codes
    @GetMapping("/distinct/names")
    public ResponseEntity<List<String>> getDistinctSubjectNames() {
        List<String> distinctNames = subjectRepository.findDistinctSubjectNames();
        return ResponseEntity.ok(distinctNames);
    }

}
