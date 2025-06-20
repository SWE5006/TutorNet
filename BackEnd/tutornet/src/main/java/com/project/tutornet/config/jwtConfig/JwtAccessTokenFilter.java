package com.project.tutornet.config.jwtConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tutornet.config.RSAKeyRecord;
import com.project.tutornet.dto.TokenType;
import com.project.tutornet.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtAccessTokenFilter extends OncePerRequestFilter {

  private final RSAKeyRecord rsaKeyRecord;
  private final JwtTokenUtils jwtTokenUtils;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      log.info("[JwtAccessTokenFilter:doFilterInternal] :: Started ");

      log.info(
          "[JwtAccessTokenFilter:doFilterInternal]Filtering the Http Request:{}",
          request.getRequestURI());

      final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

      if (authHeader == null) {
        filterChain.doFilter(request, response);
        log.info("[JwtAccessTokenFilter:doFilterInternal] Completed");
        return;
      }
      ;

      JwtDecoder jwtDecoder = NimbusJwtDecoder
          .withPublicKey(rsaKeyRecord.rsaPublicKey())
          .build();

      if (!authHeader.startsWith(TokenType.Bearer.name())) {
        filterChain.doFilter(request, response);
        return;
      }

      final String token = authHeader.substring(7);
      final Jwt jwtToken = jwtDecoder.decode(token);

      final String userName = jwtTokenUtils.getUserName(jwtToken);

      if (!userName.isEmpty() &&
          SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = jwtTokenUtils.userDetails(userName);
        if (jwtTokenUtils.isTokenValid(jwtToken, userDetails)) {
          SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

          UsernamePasswordAuthenticationToken createdToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          createdToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          securityContext.setAuthentication(createdToken);
          SecurityContextHolder.setContext(securityContext);
        }
      }
      log.info("[JwtAccessTokenFilter:doFilterInternal] Completed");

      filterChain.doFilter(request, response);
    } catch (JwtValidationException jwtValidationException) {
      log.error(
          "[JwtAccessTokenFilter:doFilterInternal] Exception due to :{}",
          jwtValidationException.getMessage());
      response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response
          .getWriter()
          .write(
              new ObjectMapper()
                  .writeValueAsString(
                      Map.of("error", "Token is invalid or expired", "status", 406)));
      // return;
      // throw new
      // ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,jwtValidationException.getMessage());
    }
  }
}
