package com.project.tutornet.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByUserInfoId(UUID userInfoId);
    Optional<Student> findByUserInfoEmailAddress(String emailAddress);
}
