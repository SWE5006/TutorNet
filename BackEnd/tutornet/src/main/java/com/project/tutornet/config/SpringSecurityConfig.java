package com.project.tutornet.config;

import java.util.List;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.project.tutornet.component.AuthLoggingFilter;
import com.project.tutornet.component.CustomAuthProvider;
import com.project.tutornet.component.CustomAuthSuccessHandler;
import com.project.tutornet.config.jwtConfig.JwtAccessTokenFilter;
import com.project.tutornet.config.jwtConfig.JwtRefreshTokenFilter;
import com.project.tutornet.repository.RefreshTokenRepo;
import com.project.tutornet.service.impl.LogoutHandlerServiceImpl;
import com.project.tutornet.utils.JwtTokenUtils;

import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityConfig {

    private final RSAKeyRecord rsaKeyRecord;
    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    private CustomAuthProvider customAuthProvider;
    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler;
    @Autowired
    private final RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private final LogoutHandlerServiceImpl logoutHandlerService;
    @Autowired
    private AuthLoggingFilter authLoggingFilter;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyRecord.rsaPublicKey()).privateKey(rsaKeyRecord.rsaPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(String[] allowedMethods) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");

        for (String method : allowedMethods) {
            configuration.addAllowedMethod(method);
        }

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setName("JSESSIONID");
            sessionCookieConfig.setHttpOnly(true);
            sessionCookieConfig.setSecure(true); // Must be true with SameSite=None
            sessionCookieConfig.setPath("/");
        };
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sameSiteCookieCustomizer() {
        return factory -> factory.addContextCustomizers(context -> {
            Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies("None"); // Allow cross-origin session sharing
            context.setCookieProcessor(cookieProcessor);
        });
    }


    @Order(1)
    @Bean
    public SecurityFilterChain googleSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api/auth/google/**", "/oauth2/**", "/login/oauth2/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource(new String[] { "GET", "POST" })))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(authLoggingFilter, OAuth2LoginAuthenticationFilter.class)
                .oauth2Login(oauth2 -> {
                    // oath2.loginPage("http://localhost:8000").permitAll();
                    oauth2.successHandler(customAuthSuccessHandler);
                }).build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain signInSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/auth/sign-in/**"))
                .cors(cors -> cors.configurationSource(corsConfigurationSource(new String[] { "GET", "POST" })))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.anyRequest().authenticated();
                }).httpBasic(withDefaults()).authenticationProvider(customAuthProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, authException) -> response
                            .sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
                })
                .build();
    }

    @Order(3)
    @Bean
    public SecurityFilterChain registerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/auth/sign-up/**"))
                .cors(cors -> cors.configurationSource(corsConfigurationSource(new String[] { "POST"  })))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Order(4)
    @Bean
    public SecurityFilterChain refreshTokenSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/auth/refresh-token/**"))
                .cors(cors -> cors.configurationSource(corsConfigurationSource(new String[] { "POST"  })))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRefreshTokenFilter(rsaKeyRecord, jwtTokenUtils, refreshTokenRepo),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:refreshTokenSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .httpBasic(withDefaults())
                .build();
    }

    @Order(5)
    @Bean
    public SecurityFilterChain logoutSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/auth/logout/**"))
                .cors(cors -> cors.configurationSource(corsConfigurationSource(new String[] { "POST" })))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils),
                        UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandlerService)
                        .logoutSuccessHandler(
                                ((request, response, authentication) -> SecurityContextHolder.clearContext())))
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:logoutSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .build();
    }

    @Order(6)
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/**"))
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource(new String[] { "GET", "POST", "DELETE" })))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:apiSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .httpBasic(withDefaults())
                .build();
    }

}