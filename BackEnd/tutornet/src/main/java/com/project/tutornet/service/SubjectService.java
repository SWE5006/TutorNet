package com.project.tutornet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.entity.Subject;
import com.project.tutornet.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SubjectService {

  @Autowired
    private SubjectRepository subjectRepository;
    
    public List<Subject> getDistinctSubjects() {
        return subjectRepository.findDistinctSubjects();
    }
    

    
    public List<String> getDistinctSubjectCodes() {
        return subjectRepository.findDistinctSubjectCodes();
    }

        public List<String> getDistinctSubjectNames() {
        return subjectRepository.findDistinctSubjectNames();
    }

    

}
