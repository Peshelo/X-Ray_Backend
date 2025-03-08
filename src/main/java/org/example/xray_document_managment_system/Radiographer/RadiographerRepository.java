package org.example.xray_document_managment_system.Radiographer;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RadiographerRepository extends JpaRepository<Radiographer,Long> {
    List<Radiographer> findAllByHospital_Id(Long id);

    Optional<Radiographer> findByEmail(String email);
}
