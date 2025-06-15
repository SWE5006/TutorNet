package com.project.tutornet.service;

import com.project.tutornet.dto.InterestRequestDto;
import com.project.tutornet.entity.Interest;
import com.project.tutornet.entity.InterestTimeSlot;
import com.project.tutornet.repository.InterestRepository;
import com.project.tutornet.dto.TimeSlotDto;
import com.project.tutornet.repository.UserInfoRepository;
import com.project.tutornet.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public void saveInterest(InterestRequestDto dto) {
        // dto.getUserId() 现在是 email
        String email = null;
        if (dto.getUserId() != null) {
            email = dto.getUserId().toString();
        }
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("User email is required");
        }
        UserInfoEntity user = userInfoRepository.findByEmailAddress(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found for email: " + email);
        }
        Interest interest = new Interest();
        interest.setUserId(user.getId());
        interest.setSubjectId(dto.getSubjectId());
        // 兼容多角色，取第一个
        String roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            String role = roles.contains(",") ? roles.split(",")[0] : roles;
            interest.setRole(role);
        }
        List<InterestTimeSlot> slots = dto.getAvailableTimeSlots().stream().map(slotDto -> {
            InterestTimeSlot slot = new InterestTimeSlot();
            slot.setStartTime(slotDto.getStartTime());
            slot.setEndTime(slotDto.getEndTime());
            slot.setDayOfWeek(slotDto.getDayOfWeek());
            slot.setInterest(interest);
            return slot;
        }).collect(Collectors.toList());
        interest.setTimeSlots(slots);
        interestRepository.save(interest);
    }
} 