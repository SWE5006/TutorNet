package com.project.tutornet;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.tutornet.config.jwtConfig.JwtTokenGenerator;
import com.project.tutornet.entity.UserInfoEntity;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

     @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    private String generateAuthToken() {
        // Mock Authentication object
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("kokyawlin@gmail.com");
       // Mockito.when(authentication.getAuthorities()).thenReturn(java.util.Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_TUTOR")));

        // Mock UserInfoEntity
        UserInfoEntity userInfo = new UserInfoEntity();
        UUID id = UUID.randomUUID(); // Generate a UUID
        userInfo.setId(id);

        // Generate token using JwtTokenGenerator
        return "Bearer " + jwtTokenGenerator.generateAccessToken(authentication, userInfo);
    }


    @Test
    public void testGetCurrentProfile() throws Exception {
        
        String authToken = generateAuthToken();
        
        // Perform GET request and verify response
        mockMvc.perform(get("/api/profile/current/kokyawlin@gmail.com")
                .header("Authorization", authToken) // Add the Authorization header
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}