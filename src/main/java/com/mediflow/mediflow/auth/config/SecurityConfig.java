package com.mediflow.mediflow.auth.config;

import com.mediflow.mediflow.auth.security.JwtAuthenticationFilter;
import com.mediflow.mediflow.auth.security.JwtService;
import com.mediflow.mediflow.auth.service.TokenBlacklistService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

      @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            TokenBlacklistService tokenBlacklistService
    ) {

        return new JwtAuthenticationFilter(jwtService, tokenBlacklistService);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,  JwtAuthenticationFilter jwtFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/register",
                    "/auth/login",
                    "/auth/forgot-password",
                    "/auth/reset-password",
                    "/auth/verify-email",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/swagger-resources/**"
                ).permitAll()
                .requestMatchers(
                    "/auth/change-password",
                    "/auth/logout",
                    "/user/**"
                ).authenticated()
                .anyRequest().authenticated()
            )
             .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return http.build();
    }
}
