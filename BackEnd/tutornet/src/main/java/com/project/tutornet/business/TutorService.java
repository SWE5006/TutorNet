package com.project.tutornet.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.repository.TutorSubjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TutorService {

    @Autowired
    private TutorSubjectRepository tutorSubjectRepository;


   
}
