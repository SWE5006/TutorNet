package com.project.tutornet.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>  {
    
}
