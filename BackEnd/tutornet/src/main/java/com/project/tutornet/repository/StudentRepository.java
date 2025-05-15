package com.project.tutornet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, String>  {
// Custom query methods can be added here if needed
}
