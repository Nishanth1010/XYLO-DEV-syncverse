/**
 * 
 */
package com.example.syncverse.config;

/**
 * Author: Nishanth Selvam
 * Date: Dec 18, 2024
 * Time: 3:56:22 PM
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/fetch/**").permitAll() // Allow public access to /api/leads
                .anyRequest().authenticated() // Secure all other endpoints
            );
            
        return http.build();
    }
}
