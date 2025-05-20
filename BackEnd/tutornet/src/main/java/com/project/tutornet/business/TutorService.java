package com.project.tutornet.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.repository.TutorRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.project.tutornet.model.Tutor;

@Service
@Slf4j
@Transactional
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

public List<Tutor> getTutorsBySubject(String subjectName)
{
    return tutorRepository.findBySubjects_Name(subjectName);
}
   
}
