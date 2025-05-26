package com.project.tutornet.repository;
import java.util.UUID;

import com.project.tutornet.entity.AvailableSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, UUID>  {
    boolean existsByAvailableId (UUID availableId);
}
