package org.example.xray_document_managment_system.Admin;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Hospital Admin Managment", description = "Manage hospital admins")

@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Admin>> getAll() {
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getById(Long id) {
        Admin admin = adminService.getById(id);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Long id) {
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Admin> create(Admin admin) {
        Admin createdAdmin = adminService.create(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(Long id, Admin admin) {
        Admin updatedAdmin = adminService.update(id, admin);
        return ResponseEntity.ok(updatedAdmin);
    }
}
