package com.project.tutornet.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, UUID> {
    List<TimeSlot> findByStudentId(UUID studentId);
    List<TimeSlot> findByTutorId(UUID tutorId);
    List<TimeSlot> findByTutorIdAndStatus(UUID tutorId, String status);

     
} 