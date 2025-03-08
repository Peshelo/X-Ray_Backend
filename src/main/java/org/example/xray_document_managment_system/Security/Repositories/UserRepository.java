package org.example.xray_document_managment_system.Security.Repositories;

import org.example.xray_document_managment_system.Security.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<Users> findByEmail(String email);


    boolean existsByEmail(String email);
}



