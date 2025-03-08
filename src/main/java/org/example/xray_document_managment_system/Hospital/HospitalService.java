package org.example.xray_document_managment_system.Hospital;


import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Admin.Admin;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    public Hospital getById(Long id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found"));
    }

    public void deleteById(Long id) {
        hospitalRepository.deleteById(id);
    }

    public Hospital create(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital update(Long id, Hospital hospital) {
        Hospital existingHospital = hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found"));
        existingHospital.setName(hospital.getName());
        existingHospital.setAddress(hospital.getAddress());
        existingHospital.setPhoneNumber(hospital.getPhoneNumber());
        existingHospital.setEmail(hospital.getEmail());
        existingHospital.setWebsite(hospital.getWebsite());
        return hospitalRepository.save(existingHospital);
    }

    public Optional<Hospital> getHospitalByAdmin(Admin admin) {
        return hospitalRepository.findByAdmin(admin);
    }
}
