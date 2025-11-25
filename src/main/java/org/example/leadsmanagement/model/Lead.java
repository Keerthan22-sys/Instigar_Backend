package org.example.leadsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)  // To display non-null
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String stage;
    private String source;
    private String assignedTo;
    @Column(columnDefinition = "TEXT")
    private String notes;
    private String email;
    private String phone;
    private String course;
    private String dateAdded;
    private String fatherName;
    private String motherName;
    private String fatherPhoneNumber;
    private String motherPhoneNumber;
    private String address;
    private String previousInstitution;
    private String marksObtained;
    @CreatedDate
    @Column(updatable = false)
    private java.util.Date createdAt;
    @LastModifiedDate
    private java.util.Date updatedAt;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
    private String type;
    private String amount;
}
