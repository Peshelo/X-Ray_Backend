package org.example.xray_document_managment_system.Radiograph;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.xray_document_managment_system.Patient.Patient;
import org.example.xray_document_managment_system.Physician.Physician;
import org.example.xray_document_managment_system.Radiographer.Radiographer;

import java.time.LocalDate;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Radiograph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "image_url")
    private String ImageUrl;

    private String description;

    private LocalDate date;

    private String radiographerNotes;


    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "radiographer_id", nullable = false)
    private Radiographer radiographer;

    @ManyToOne
    @JoinColumn(name = "physician_id", nullable = true)
    private Physician physician;


}
