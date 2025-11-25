package org.example.leadsmanagement.service;

import org.example.leadsmanagement.model.Lead;
import org.example.leadsmanagement.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public Lead saveLead(Lead lead, String username) {
        lead.setCreatedBy(username);
        lead.setUpdatedBy(username);
        return leadRepository.save(lead);
    }

    public void deleteLead(Long id, String username) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));
        lead.setUpdatedBy(username);
        leadRepository.delete(lead);
    }

    public List<Lead> filterLeads(String stage, String source, String startDate, String endDate, String type) {
        if (stage != null) return leadRepository.findByStage(stage);
        if (source != null) return leadRepository.findBySource(source);
        if (startDate != null && endDate != null) return leadRepository.findByDateAddedBetween(startDate, endDate);
        if(type != null) return leadRepository.findByType(type);
        return getAllLeads();
    }
}