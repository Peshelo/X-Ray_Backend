package org.example.xray_document_managment_system.Radiograph;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Patient.Patient;
import org.example.xray_document_managment_system.Patient.PatientRepository;
import org.example.xray_document_managment_system.Radiographer.Radiographer;
import org.example.xray_document_managment_system.Radiographer.RadiographerRepository;
import org.example.xray_document_managment_system.Radiographer.RadiographerService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RadiographService {

    private final RadiographRepository radiographRepository;
    private final PatientRepository patientRepository;
    private final RadiographerRepository radiographerRepository;

    public List<Radiograph> getAll() {
        return radiographRepository.findAll();
    }

    public Radiograph getById(Long id) {
        boolean exists = radiographRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Radiograph not found with id: " + id);
        }
        return radiographRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        radiographRepository.deleteById(id);
    }

    @Transactional
    public Radiograph create(Radiograph radiograph, Long patientId, String radiographerEmail) {
        // Verify no duplicate ImageUrl


        Radiographer radiographer = radiographerRepository.findByEmail(radiographerEmail)
                .orElseThrow(() -> new RuntimeException("Radiographer not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        radiograph.setRadiographer(radiographer);
        radiograph.setPatient(patient);

        return radiographRepository.save(radiograph);
    }


    public List<Radiograph> getAllByPatientId(Long id) {
        return radiographRepository.findAllByPatient_Id(id);
    }

    public void editById(Long id,String notes) {
        Radiograph radiograph = radiographRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Record not found")
        );

       radiograph.setDescription(notes);
       radiographRepository.save(radiograph);
    }
}
