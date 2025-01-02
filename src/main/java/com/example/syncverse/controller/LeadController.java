/**
 * 
 */
package com.example.syncverse.controller;

import java.util.List;

/**
 * Author: Nishanth Selvam
 * Date: Dec 18, 2024
 * Time: 2:52:15 PM
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.syncverse.entity.Lead;
import com.example.syncverse.repository.LeadRepository;
import com.example.syncverse.service.LeadService;

@RestController
public class LeadController {

    @Autowired
    private LeadService leadService;

    @Autowired
    private LeadRepository leadRepository;

    // Fetch leads from an external API
    @GetMapping("/fetch")
    public String fetchLeads(@RequestParam String url) {
        leadService.fetchAndSaveLeads(url);
        return "Leads fetched and saved successfully!";
    }

    // Create a new lead (accepts createdAt from the request)
    @PostMapping
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        Lead savedLead = leadRepository.save(lead);
        return ResponseEntity.ok(savedLead);
    }

    // Get all leads
    @GetMapping
    public ResponseEntity<List<Lead>> getAllLeads() {
        return ResponseEntity.ok(leadRepository.findAll());
    }
}