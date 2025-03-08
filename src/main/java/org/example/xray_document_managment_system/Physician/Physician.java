package org.example.xray_document_managment_system.Physician;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.xray_document_managment_system.Hospital.Hospital;
import org.example.xray_document_managment_system.Security.Models.Users;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Physician extends Users {

    private String speciality;

    private String department;

    private String fingerprintData;

    private String language;


    @ManyToOne
    @JsonIgnore
    private Hospital hospital;

    // Getters and Setters
}
