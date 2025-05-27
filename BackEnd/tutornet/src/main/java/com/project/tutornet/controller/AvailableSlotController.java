package com.project.tutornet.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.AvailableSlotRequest;
import com.project.tutornet.entity.AvailableSlot;
import com.project.tutornet.service.AvailableSlotService;

@RestController
@RequestMapping("/api/availableslot")
public class AvailableSlotController {

  @Autowired
  private AvailableSlotService availableSlotService;

  @GetMapping("/tutor/{tutorId}")
  public ResponseEntity<List<AvailableSlotRequest>> getAvailableSlotsByTutorId(
    @PathVariable UUID tutorId
  ) {
    try {
      List<AvailableSlot> availableSlots = availableSlotService.getAvailableSlotsByTutorId(
        tutorId
      );
      List<AvailableSlotRequest> slotDtos = availableSlots
        .stream()
        .map(AvailableSlotRequest::new)
        .collect(Collectors.toList());
      return ResponseEntity.ok(slotDtos);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
