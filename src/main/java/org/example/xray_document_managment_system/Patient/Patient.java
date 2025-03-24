package org.example.xray_document_managment_system.Patient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.xray_document_managment_system.Patient.userbiometrics.UserBiometrics;
import org.example.xray_document_managment_system.Radiograph.Radiograph;
import org.example.xray_document_managment_system.Radiographer.Radiographer;
import org.example.xray_document_managment_system.Security.Models.Users;
import org.example.xray_document_managment_system.Utils.Enums.Gender;
import org.example.xray_document_managment_system.Utils.Enums.Languages;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patient extends Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileUrl;
    private String nationalIdUrl;
    private String fingerprintData;
    private Languages language;

    @OneToOne
    private UserBiometrics userBiometrics;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Radiograph> radiographs = new ArrayList<>();

//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
//    private List<Appointment> appointments;

//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
//    private List<XrayRequestForm> xrayRequestForms;

    // Getters and Setters

    public void addRadiograph(Radiograph radiograph) {
        radiographs.add(radiograph);
        radiograph.setPatient(this);
    }

    public void removeRadiograph(Radiograph radiograph) {
        radiographs.remove(radiograph);
        radiograph.setPatient(null);
    }
}
