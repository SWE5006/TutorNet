package com.project.tutornet.service;

import com.project.tutornet.dto.AuthResponse;
import com.project.tutornet.dto.UserRegistrationRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response);
    AuthResponse registerUser(UserRegistrationRequest userRegistrationRequest);
}
