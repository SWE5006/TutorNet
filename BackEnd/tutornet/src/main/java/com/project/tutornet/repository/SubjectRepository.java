package com.project.tutornet.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Subject;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID>  {
    boolean existsBySubjectId (UUID subjectId);
}
