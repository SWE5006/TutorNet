package com.project.tutornet.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.StudentSubject;


@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, UUID>  {
   //custom functions here
}
