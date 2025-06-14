package com.project.tutornet;


import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tutornet.entity.Student;
import com.project.tutornet.repository.StudentRepository;
import com.project.tutornet.repository.TutorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        studentRepository.deleteAll();
        tutorRepository.deleteAll();
    }

    @Test
    public void testRegisterStudent() throws Exception {
        Student student = new Student();
        student.setUsername("Alice");
        student.setEmailAddress("alice@student.com");
        student.setPassword("securepass");
        student.setAge(20);
        student.setClassLevel("Secondary");
        student.setMobileNumber("123456");

        mockMvc.perform(post("/api/students/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("Alice")))
               ;
    }
}
