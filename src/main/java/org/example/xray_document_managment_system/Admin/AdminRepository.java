package org.example.xray_document_managment_system.Admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Optional<Admin> findAdminByEmail(String username);
}
