package org.example.xray_document_managment_system.Physician;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface PhysicianRepository extends JpaRepository<Physician,Long> {
    Optional<Physician> findByEmail(String email);
}
