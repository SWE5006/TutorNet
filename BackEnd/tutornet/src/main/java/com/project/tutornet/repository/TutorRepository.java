package com.project.tutornet.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID>  {
    //for custom search
    List<Tutor> findBySubjects_Name(String subjectName);

    @Query("SELECT t FROM Tutor t WHERE LOWER(t.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(t.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Tutor> searchTutorsByName(@Param("name") String name);
}
