package com.project.tutornet.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID> {
    //List<Tutor> findBySubjectsContaining(String subjectName);

    @Query("SELECT t FROM Tutor t WHERE t.userInfo.id = :userInfoId")
    Optional<Tutor> findByUserInfoId(@Param("userInfoId") UUID userInfoId);

    Optional<Tutor> findByUserInfoEmailAddress(String emailAddress);

    @Query("SELECT DISTINCT t FROM Tutor t JOIN t.subjects s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :subjectName, '%'))")
    List<Tutor> findTutorsBySubjectName(@Param("subjectName") String subjectName);
}
