package com.project.tutornet.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID>  {
    boolean existsByUserId(UUID userId);
}
