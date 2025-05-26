package com.project.tutornet.repository;

import java.util.List;
import java.util.UUID;

import com.project.tutornet.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID>  {
    //for custom search
    List<Tutor> findBySubjects_Name(String subjectName);

    @Query("SELECT t FROM Tutor t WHERE LOWER(t.username) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Tutor> searchTutorsByName(@Param("name") String name);
}
