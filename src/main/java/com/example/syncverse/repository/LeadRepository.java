package com.example.syncverse.repository;

/**
 * Author: Nishanth Selvam
 * Date: Dec 18, 2024
 * Time: 2:51:25 PM
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.syncverse.entity.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

}