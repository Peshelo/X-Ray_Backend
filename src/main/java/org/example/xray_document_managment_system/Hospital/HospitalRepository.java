package org.example.xray_document_managment_system.Hospital;

import org.example.xray_document_managment_system.Admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {

    Optional<Hospital> findByAdmin(Admin admin);

}
