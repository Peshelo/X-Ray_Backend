package org.example.xray_document_managment_system.HealthRecord;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.xray_document_managment_system.Radiograph.Radiograph;
import org.example.xray_document_managment_system.Patient.Patient;
import org.example.xray_document_managment_system.Physician.Physician;
import org.example.xray_document_managment_system.Radiographer.Radiographer;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String description;

    private String physicianNotes;

//    @OneToMany
//    private List<Radiograph> radiographs;
}
