package org.example.xray_document_managment_system.Radiograph;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RadiographRepository extends JpaRepository<Radiograph,Long> {

    List<Radiograph> findAllByPatient_Id(Long id);
}
