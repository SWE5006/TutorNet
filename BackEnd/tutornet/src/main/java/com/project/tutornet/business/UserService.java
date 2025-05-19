package com.project.tutornet.business;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.dto.TutorRequest;
import com.project.tutornet.model.Student;
import com.project.tutornet.model.Tutor;
import com.project.tutornet.model.User;
import com.project.tutornet.repository.StudentRepository;
import com.project.tutornet.repository.TutorRepository;
import com.project.tutornet.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserService {
    private UserRepository userRepository = null;


    public UserService(UserRepository userRepository,
                             StudentRepository studentRepository,
                             TutorRepository tutorRepository) {
        this.userRepository = userRepository;
       
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
   @Transactional
    public User createTutor(TutorRequest request) {
        
        User user = new User();
        Tutor tutor = new Tutor();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder().encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserRole("TUTOR");

        tutor.setQualification(request.getQualification());
        tutor.setExperienceYears(request.getExperienceYears());
        tutor.setHourlyRate(request.getHourlyRate());
        tutor.setAvailableId(request.getAvailabilityId());
        tutor.setActive(true);
        user.setTutor(tutor);

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create tutor", e);
        }
    }

    @Transactional
    public User createStudent(StudentRequest request) {
       

        User user = new User();
        Student student = new Student();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder().encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserRole("STUDENT");

        student.setAge(request.getAge());
        student.setClassLevel(request.getClassLevel());
        user.setStudent(student);

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create student", e);
        }
    }
    
    
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
