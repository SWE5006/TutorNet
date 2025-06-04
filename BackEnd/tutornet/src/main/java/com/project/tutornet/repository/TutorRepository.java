package com.project.tutornet.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID> {
    List<Tutor> findByTeachingSubjectsContaining(String subjectName);

    @Query("SELECT t FROM Tutor t WHERE LOWER(t.userInfo.username) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Tutor> searchTutorsByName(@Param("name") String name);

    Optional<Tutor> findByUserInfoId(UUID userInfoId);
}
