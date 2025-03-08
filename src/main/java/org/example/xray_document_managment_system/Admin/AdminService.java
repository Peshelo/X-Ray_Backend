package org.example.xray_document_managment_system.Admin;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Security.Models.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public List<Admin> getAll() {
        return adminRepository.findAll();
    }



    public void deleteById(Long id) {
        boolean exists = adminRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Admin with id " + id + " does not exist");
        }
        adminRepository.deleteById(id);
    }

    public Admin getById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin create(Admin admin) {
        admin.setRoles(List.of(Role.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public Admin update(Long id, Admin admin) {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);
        if (existingAdmin == null) {
            throw new IllegalStateException("Admin with id " + id + " does not exist");
        }
        existingAdmin.setFirstname(admin.getFirstname());
        existingAdmin.setLastname(admin.getLastname());
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPassword(admin.getPassword());
        return adminRepository.save(existingAdmin);
    }

    public Optional<Admin> findByEmail(String username) {
        return adminRepository.findAdminByEmail(username);
    }
}
