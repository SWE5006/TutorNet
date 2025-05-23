package com.project.tutornet.business;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.model.Tutor;
import com.project.tutornet.repository.TutorRepository;

import lombok.extern.slf4j.Slf4j;

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

 public List<Tutor> searchTutorsByName(String name) {
        return tutorRepository.searchTutorsByName(name);
    }
}
