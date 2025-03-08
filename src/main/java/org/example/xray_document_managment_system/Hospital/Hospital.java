package org.example.xray_document_managment_system.Hospital;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.xray_document_managment_system.Admin.Admin;
import org.example.xray_document_managment_system.Physician.Physician;
import org.example.xray_document_managment_system.Radiographer.Radiographer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    private String email;

    private String website;

    @OneToOne
    @JoinColumn(name = "admin_id") // Foreign key to reference the Admin
    private Admin admin;


    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Radiographer> radiographers = new ArrayList<>();

    // Helper methods to manage bi-directional relationships
    public void addRadiographer(Radiographer radiographer) {
        radiographers.add(radiographer);
        radiographer.setHospital(this);
    }

    public void removeRadiographer(Radiographer radiographer) {
        radiographers.remove(radiographer);
        radiographer.setHospital(null);
    }

}
