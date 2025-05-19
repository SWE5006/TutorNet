package com.project.tutornet.business;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.model.Tutor;
import com.project.tutornet.model.TutorSubject;
import com.project.tutornet.repository.TutorSubjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TutorService {

    @Autowired
    private TutorSubjectRepository tutorSubjectRepository;


    public List<Tutor> findTutorsBySubjectName(String subjectName) {
        List<TutorSubject> tutorSubjects = tutorSubjectRepository.findBySubjectName(subjectName);
        return tutorSubjects.stream()
                .map(TutorSubject::getTutor)
                .distinct()
                .collect(Collectors.toList());
    }

}
