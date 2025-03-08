package org.example.xray_document_managment_system.Hospital;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.xray_document_managment_system.Admin.Admin;
import org.example.xray_document_managment_system.Admin.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
@Data
@Tag(name = "Hospital Managment", description = "Manage hospitals")
public class HospitalController {

    private final HospitalService hospitalService;
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Hospital>> getAll() {
        List<Hospital> hospitals = hospitalService.getAll();
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getById(Long id) {
        Hospital hospital = hospitalService.getById(id);
        return ResponseEntity.ok(hospital);
    }

    @GetMapping("/get-by-admin")
    public ResponseEntity<Hospital> getByAdmin(Principal principal) {

        System.out.println(principal.getName().toString());
        Optional<Admin> adminOptional = adminService.findByEmail(principal.getName());

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return hospitalService.getHospitalByAdmin(admin)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    @PutMapping("/assign-admin")
//    public ResponseEntity<Hospital> assignAdmin(Principal principal) {
//        log.info(principal.toString());
//        Authentication authentication = (Authentication) principal;
//        Admin admin = (Admin) authentication.getPrincipal();
//
//        Optional<Hospital> hospital = hospitalService.getHospitalByAdmin(admin);
//        // Fetch the hospital associated with the admin
//        return hospitalService.getHospitalByAdmin(admin)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PutMapping("/{hospitalId}/assign-admin/{adminId}")
    public ResponseEntity<Hospital> assignAdminToHospital(
            @PathVariable Long hospitalId,
            @PathVariable Long adminId) {

        Hospital hospital = hospitalService.getById(hospitalId);
        Admin admin = adminService.getById(adminId);

        if (hospital != null && admin != null) {

            // Assign the admin to the hospital
            hospital.setAdmin(admin);

            // Save the updated hospital
            Hospital updatedHospital = hospitalService.create(hospital);

            return ResponseEntity.ok(updatedHospital);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Long id) {
        hospitalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Hospital> create(Hospital hospital) {
        Hospital createdHospital = hospitalService.create(hospital);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHospital);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> update(Long id, Hospital hospital) {
        Hospital updatedHospital = hospitalService.update(id, hospital);
        return ResponseEntity.ok(updatedHospital);
    }



}
