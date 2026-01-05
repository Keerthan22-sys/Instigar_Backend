package org.example.leadsmanagement.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.leadsmanagement.model.Lead;
import org.example.leadsmanagement.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/leads")
@SecurityRequirement(name = "bearerAuth")
//@CrossOrigin(origins = "https://yourdomain.com")
//@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<List<Lead>> getAllLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Lead> createLead(@Valid @RequestBody Lead lead, Principal principal) {
        return ResponseEntity.ok(leadService.saveLead(lead, principal.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Lead> updateLead(@PathVariable Long id, @Valid @RequestBody Lead lead, Principal principal) {
        Lead existingLead = leadService.getAllLeads().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lead not found with id: " + id));

        existingLead.setName(lead.getName());
        existingLead.setStage(lead.getStage());
        existingLead.setSource(lead.getSource());
        existingLead.setAssignedTo(lead.getAssignedTo());
        existingLead.setNotes(lead.getNotes());
        existingLead.setEmail(lead.getEmail());
        existingLead.setPhone(lead.getPhone());
        existingLead.setCourse(lead.getCourse());
        existingLead.setDateAdded(lead.getDateAdded());
        existingLead.setUpdatedBy(principal.getName());
        existingLead.setType(lead.getType());
        existingLead.setFatherName(lead.getFatherName());
        existingLead.setMotherName(lead.getMotherName());
        existingLead.setFatherPhoneNumber(lead.getFatherPhoneNumber());
        existingLead.setMotherPhoneNumber(lead.getMotherPhoneNumber());
        existingLead.setAddress(lead.getAddress());
        existingLead.setPreviousInstitution(lead.getPreviousInstitution());
        existingLead.setMarksObtained(lead.getMarksObtained());
        existingLead.setAmount(lead.getAmount());

        Lead updatedLead = leadService.saveLead(existingLead, principal.getName());
        return ResponseEntity.ok(updatedLead);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id, Principal principal) {
        leadService.deleteLead(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<List<Lead>> filterLeads(
            @RequestParam(required = false) String stage,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(leadService.filterLeads(stage, source, startDate, endDate,type));
    }
}