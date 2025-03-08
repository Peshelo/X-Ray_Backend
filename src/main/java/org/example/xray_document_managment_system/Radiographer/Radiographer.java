package org.example.xray_document_managment_system.Radiographer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.xray_document_managment_system.Hospital.Hospital;
import org.example.xray_document_managment_system.Radiograph.Radiograph;
import org.example.xray_document_managment_system.Security.Models.Users;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Tag(name = "Radiographer Personell Managment", description = "Manage radiographer stuff")
public class Radiographer extends Users {

    private String profileUrl;

    private String department;

    private String fingerprintData;

    private String language;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;


    // Getters and Setters
}
