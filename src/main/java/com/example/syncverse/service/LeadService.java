
package com.example.syncverse.service;

/**
 * Author: Nishanth Selvam
 * Date: Dec 18, 2024
 * Time: 2:51:54 PM
 */


import com.example.syncverse.entity.Lead;
import com.example.syncverse.repository.LeadRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Define a custom DateTimeFormatter to handle the 'Z' in the timestamp
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .optionalStart()
            .appendPattern("X")
            .optionalEnd()
            .toFormatter();

    public void fetchAndSaveLeads(String sourceUrl) {
        ResponseEntity<String> response = restTemplate.getForEntity(sourceUrl, String.class);
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode leads = root.get("data");

            if (leads != null && leads.isArray()) {
                for (JsonNode node : leads) {
                    Lead lead = new Lead();
                    lead.setId(node.get("id").asLong());
                    lead.setName(node.get("name").asText());
                    lead.setEmail(node.get("email").isNull() ? null : node.get("email").asText());

                    String phone = node.get("phone").asText();
                    if (!phone.startsWith("+91")) {
                        phone = "+91" + phone;
                    }
                    lead.setPhone(phone);

                    // Parse 'createdAt' with the custom formatter
                    JsonNode createdAtNode = node.get("createdAt");
                    if (createdAtNode != null && !createdAtNode.isNull()) {
                        lead.setCreatedAt(LocalDateTime.parse(createdAtNode.asText(), formatter));
                    }

                    leadRepository.save(lead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}