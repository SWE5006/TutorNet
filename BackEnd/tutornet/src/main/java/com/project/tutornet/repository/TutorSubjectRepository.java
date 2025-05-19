package com.project.tutornet.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.TutorSubject;


@Repository
public interface TutorSubjectRepository extends JpaRepository<TutorSubject, UUID>  {
    List<TutorSubject> findBySubjectName(String subjectName);
}
