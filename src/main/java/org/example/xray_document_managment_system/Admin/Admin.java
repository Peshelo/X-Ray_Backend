package org.example.xray_document_managment_system.Admin;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import org.example.xray_document_managment_system.Hospital.Hospital;
import org.example.xray_document_managment_system.Security.Models.Users;

@Entity
@EqualsAndHashCode(callSuper = true)
public class Admin extends Users {

    @OneToOne(mappedBy = "admin")
    private Hospital hospital;

}
