package com.project.tutornet.repository;
import java.util.List;
import java.util.UUID;

import com.project.tutornet.entity.AvailableSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, UUID>  {
//    boolean existsByAvailableId (UUID availableId);
//    List<AvailableSlot> findByTutors_UserId(UUID tutorId);
//    List<AvailableSlot> findByTutors_UserIdAndSlotStatus(UUID tutorId, String slotStatus);
}
