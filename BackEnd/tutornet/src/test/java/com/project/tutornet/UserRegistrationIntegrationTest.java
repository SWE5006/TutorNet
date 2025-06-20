package com.project.tutornet;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tutornet.dto.StudentRequest;
import com.project.tutornet.dto.TutorRequest;

import jakarta.transaction.Transactional;


@SpringBootTest
@AutoConfigureMockMvc

@Transactional
@Rollback
public class UserRegistrationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testStudentSignUp() throws Exception {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setUsername("studentUser");
        studentRequest.setPassword("P@ssword123");
        studentRequest.setEmailAddress("integrationtest@aa.com");
        studentRequest.setBio("Good Student");
      
        

        mockMvc.perform(post("/api/auth/sign-up/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequest).getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    public void testTutorSignUp() throws Exception {
        List<String> teachingSubjects = new ArrayList<>();
        teachingSubjects.add("Mathemathics");
        teachingSubjects.add("English");
        TutorRequest tutorRequest = new TutorRequest();
        tutorRequest.setUsername("tutorUser");
        tutorRequest.setPassword("P@ssword123");
        tutorRequest.setEmailAddress("integrationtest@bb.com");
        tutorRequest.setTeachingSubjects(teachingSubjects);

        mockMvc.perform(post("/api/auth/sign-up/tutor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tutorRequest)))
                .andExpect(status().isOk());
    }

   
}
