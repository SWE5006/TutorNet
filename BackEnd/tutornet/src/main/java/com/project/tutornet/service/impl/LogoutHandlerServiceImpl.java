package com.project.tutornet.service.impl;


import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.project.tutornet.config.RSAKeyRecord;
import com.project.tutornet.dto.TokenType;
import com.project.tutornet.entity.RefreshTokenEntity;
import com.project.tutornet.repository.RefreshTokenRepo;
import com.project.tutornet.utils.JwtTokenUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutHandlerServiceImpl implements LogoutHandler {

    private final RefreshTokenRepo refreshTokenRepo;
    private final RSAKeyRecord rsaKeyRecord;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

       

       

         // Remove the refresh_token cookie from client
        Cookie deleteCookie = new Cookie("refresh_token", null);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(true);
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);


 final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!authHeader.startsWith(TokenType.Bearer.name())){
            return;
        }
        //get userid from jwt token
        JwtDecoder jwtDecoder = NimbusJwtDecoder
                .withPublicKey(rsaKeyRecord.rsaPublicKey())
                .build();

        String tokenStr = authHeader.substring(7);
        Jwt jwtToken = jwtDecoder.decode(tokenStr);

        String id = (String) jwtToken.getClaims().get("userid");

        System.out.print("This is the Id:::::::::" + id);

        try
        {
        UUID userId = UUID.fromString(id);

        List<RefreshTokenEntity> tokenList  = refreshTokenRepo.findAllByUserId(userId);
        //set refresh_token status to revoked
        tokenList.forEach(token -> {
            token.setRevoked(true);
            refreshTokenRepo.save(token);
        });
        } catch(Exception ex)
{           System.out.print(ex.getMessage());
        }        
    }
}