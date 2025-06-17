package com.project.tutornet;

import com.project.tutornet.entity.UserInfoEntity;
import com.project.tutornet.model.UserDetailModel;
import com.project.tutornet.repository.UserRepository;
import com.project.tutornet.model.UserDetailModel;
import com.project.tutornet.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInfoServiceImplTest {

    private UserRepository userRepository;
    private UserInfoServiceImpl userInfoService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userInfoService = new UserInfoServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        String email = "fzy11fuful11@gmail.com";
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setEmailAddress(email);
//        userInfoEntity.setPassword("encodedPassword");
        UserDetailModel fakeUser = new UserDetailModel(userInfoEntity);
        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(userInfoEntity));

        UserDetailModel userDetails = (UserDetailModel) userInfoService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
//        assertEquals("encodedPassword", userDetails.getPassword());

        verify(userRepository, times(1)).findByEmailAddress(email);
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userInfoService.loadUserByUsername(email)
        );

        assertEquals("UserEmail: notfound@example.com does not exist", exception.getMessage());
        verify(userRepository, times(1)).findByEmailAddress(email);
    }
}
