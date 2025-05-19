package com.project.tutornet.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>  {
    //for custom search
}
