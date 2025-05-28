package com.project.tutornet.repository;
import java.util.UUID;

import com.project.tutornet.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID>  {
    boolean existsBySubjectId (UUID subjectId);
}
