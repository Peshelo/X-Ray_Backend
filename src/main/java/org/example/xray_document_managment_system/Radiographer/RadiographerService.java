package org.example.xray_document_managment_system.Radiographer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Hospital.Hospital;
import org.example.xray_document_managment_system.Hospital.HospitalRepository;
import org.example.xray_document_managment_system.Security.Models.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RadiographerService {

    private final RadiographerRepository radiographerRepository;
    private final PasswordEncoder passwordEncoder;
    private final HospitalRepository hospitalRepository;


    public List<Radiographer> getAll() {
        return radiographerRepository.findAll();
    }

    public List<Radiographer> getAllByHospitalId(Long hospitalId) {
        return radiographerRepository.findAllByHospital_Id(hospitalId);
    }

    public Radiographer getById(Long id) {
        boolean exists = radiographerRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Radiographer not found with id: " + id);
        }
        return radiographerRepository.findById(id).orElse(null);
    }


    /**
     * Creates a new radiographer and associates it with a hospital.
     *
     * @param radiographer the radiographer to create
     * @param hospitalId   the ID of the hospital
     * @return the created radiographer
     * @throws RuntimeException if hospital not found
     */
    @Transactional
    public Radiographer create(Radiographer radiographer, Long hospitalId) {
        // Assign role and encode password
        radiographer.setRoles(List.of(Role.RADIOGRAPHER));
        radiographer.setPassword(passwordEncoder.encode(radiographer.getPassword()));

        // Fetch the hospital
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + hospitalId));

        // Associate radiographer with hospital
        hospital.addRadiographer(radiographer);

        // Save hospital (cascades to radiographer)
        hospitalRepository.save(hospital);

        return radiographer;
    }

    public void deleteById(Long id) {
        Radiographer radiographer = getById(id);
        radiographer.getHospital().removeRadiographer(radiographer);
        radiographerRepository.delete(radiographer);
    }

    public Radiographer getByEmail(String name) {
        Radiographer radiographer = radiographerRepository.findByEmail(name).get();

        return radiographer;
    }
}
