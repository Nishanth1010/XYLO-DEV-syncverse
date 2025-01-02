/**
 * 
 */
package com.example.syncverse.config;

/**
 * Author: Nishanth Selvam
 * Date: Dec 18, 2024
 * Time: 6:23:15 PM
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}