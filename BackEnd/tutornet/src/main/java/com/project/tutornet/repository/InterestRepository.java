package com.project.tutornet.repository;

import com.project.tutornet.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
} 