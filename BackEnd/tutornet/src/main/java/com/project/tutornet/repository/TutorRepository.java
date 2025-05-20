package com.project.tutornet.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID>  {
    //for custom search
    List<Tutor> findBySubjects_Name(String subjectName);
}
