package com.bank.allservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow requests from specific origins
        configuration.addAllowedOrigin("http://localhost:4200"); // Your frontend URL (Angular, React, etc.)
        
        // Allow specific HTTP methods
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        
        // Allow all headers (you can restrict this to specific headers if needed)
        configuration.addAllowedHeader("*");
        
        // Allow credentials (cookies, Authorization headers, etc.)
        configuration.setAllowCredentials(true);
        
        // Apply CORS configuration to all paths (or specific paths if necessary)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}

