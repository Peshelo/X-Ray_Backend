package org.example.xray_document_managment_system.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Optional<Patient> findByNationalId(String nationalId);

    Optional<Patient> findByEmail(String email);
}
