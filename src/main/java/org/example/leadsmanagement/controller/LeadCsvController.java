package org.example.leadsmanagement.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.leadsmanagement.model.Lead;
import org.example.leadsmanagement.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/api/leads/csv")
@SecurityRequirement(name = "bearerAuth")
//@CrossOrigin(origins = "https://yourdomain.com")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
public class LeadCsvController {
    @Autowired
    private LeadService leadService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file, Principal principal) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            List<Lead> leads = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                Lead lead = new Lead();
                lead.setName(record.get("name"));
                lead.setStage(record.get("stage"));
                lead.setSource(record.get("source"));
                lead.setAssignedTo(record.get("assignedTo"));
                lead.setNotes(record.get("notes"));
                lead.setEmail(record.get("email"));
                lead.setPhone(record.get("phone"));
                lead.setCourse(record.get("course"));
                lead.setDateAdded(record.get("dateAdded"));
                lead.setFatherName(record.get("fatherName"));
                lead.setMotherName(record.get("motherName"));
                lead.setFatherPhoneNumber(record.get("fatherPhoneNumber"));
                lead.setMotherPhoneNumber(record.get("motherPhoneNumber"));
                lead.setAddress(record.get("address"));
                lead.setPreviousInstitution(record.get("previousInstitution"));
                lead.setMarksObtained(record.get("marksObtained"));
                lead.setType(record.get("type"));
                leads.add(lead);
            }
            leads.forEach(lead -> leadService.saveLead(lead, principal.getName()));
            return ResponseEntity.ok("CSV uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading CSV: " + e.getMessage());
        }
    }
}