package com.project.tutornet.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.AvailableSlot;



@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, UUID>  {
   boolean existsByAvailableId (UUID availableId);
   List<AvailableSlot> findByTutors_Id(UUID tutorId);
   List<AvailableSlot> findByTutors_IdAndSlotStatus(UUID tutorId, String slotStatus);
}
