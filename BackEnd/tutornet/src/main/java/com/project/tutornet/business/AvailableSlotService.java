package com.project.tutornet.business;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.dto.AvailableSlotRequest;
import com.project.tutornet.dto.UpdateSlotStatusDto;
import com.project.tutornet.model.AvailableSlot;
import com.project.tutornet.repository.AvailableSlotRepository;
import com.project.tutornet.web.BookingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class AvailableSlotService {
   @Autowired
    private AvailableSlotRepository availableSlotRepository;
    
    public List<AvailableSlot> getAvailableSlotsByTutorId(UUID tutorId) {
        return availableSlotRepository.findByTutors_UserId(tutorId);
    }
    
    public List<AvailableSlot> getAvailableSlotsByTutorIdAndStatus(UUID tutorId, String status) {
        return availableSlotRepository.findByTutors_UserIdAndSlotStatus(tutorId, status);
    }

     
    
    public Optional<AvailableSlot> getSlotById(UUID slotId) {
        return availableSlotRepository.findById(slotId);
    }
    
    @Transactional
    public AvailableSlotRequest updateSlotStatus(UUID slotId, UpdateSlotStatusDto updateStatusDto) {
        Optional<AvailableSlot> slotOpt = availableSlotRepository.findById(slotId);
        if (!slotOpt.isPresent()) {
            throw new BookingException("Available slot not found with ID: " + slotId);
        }
        
        AvailableSlot slot = slotOpt.get();
        String oldStatus = slot.getSlotStatus();
        String newStatus = updateStatusDto.getSlotStatus();
        
        // Validate status change
        if (!isValidSlotStatusTransition(slot, oldStatus, newStatus)) {
            throw new BookingException("Invalid slot status transition from " + oldStatus + " to " + newStatus);
        }
        
        // Update slot status
        slot.setSlotStatus(newStatus);
        AvailableSlot savedSlot = availableSlotRepository.save(slot);
        
        return new AvailableSlotRequest(savedSlot);
    }
    
    private boolean isValidSlotStatusTransition(AvailableSlot slot, String oldStatus, String newStatus) {
    
        
        // Define valid transitions
        switch (oldStatus.toUpperCase()) {
            case "AVAILABLE":
                return true; // Available slots can be changed to any status
            case "COMPLETED":
                return newStatus.equalsIgnoreCase("AVAILABLE"); // Allow reactivation
            case "UNAVAILABLE":
                return true; // Unavailable slots can be made available again
            default:
                return true;
        }
    }
}
