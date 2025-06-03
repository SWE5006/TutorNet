package com.project.tutornet.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.Subject;



@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID>  {
    boolean existsBySubjectId (UUID subjectId);


     // Method 1: Get distinct subjects by subject code
    @Query("SELECT DISTINCT s FROM Subject s")
    List<Subject> findDistinctSubjects();
    
    
    
    // Method 3: Get distinct by subject code only
    @Query("SELECT DISTINCT s.subjectCode FROM Subject s")
    List<String> findDistinctSubjectCodes();

      // Method 3: Get distinct by subject code only
    @Query("SELECT DISTINCT s.name FROM Subject s")
    List<String> findDistinctSubjectNames();
}
