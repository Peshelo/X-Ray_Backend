package org.example.xray_document_managment_system.Physician;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Radiographer.Radiographer;
import org.example.xray_document_managment_system.Security.Models.Role;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhysicianService {

    private final PasswordEncoder passwordEncoder;

    private final PhysicianRepository physicianRepository;

    public List<Physician> getAll() {
        return physicianRepository.findAll();
    }

    public Physician getById(Long id) {
        boolean exists = physicianRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Physician not found with id: " + id);
        }
        return physicianRepository.findById(id).orElse(null);
    }

    public Physician create(Physician physician) {
        physician.setRoles(List.of(Role.PHYSICIAN));
        physician.setPassword(passwordEncoder.encode(physician.getPassword()));
        return physicianRepository.save(physician);
    }

    public void deleteById(Long id) {
        physicianRepository.deleteById(id);
    }

    public Physician getByEmail(String name) {
        Physician physician = physicianRepository.findByEmail(name).get();

        return physician;
    }
}
