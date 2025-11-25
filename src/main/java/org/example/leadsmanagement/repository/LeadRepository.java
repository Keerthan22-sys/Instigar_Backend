package org.example.leadsmanagement.repository;
import org.example.leadsmanagement.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByStage(String stage);
    List<Lead> findBySource(String source);
    List<Lead> findByDateAddedBetween(String startDate, String endDate);
    List<Lead> findByType(String type);
}