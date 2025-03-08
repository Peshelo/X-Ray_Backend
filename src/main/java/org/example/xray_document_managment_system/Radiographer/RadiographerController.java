package org.example.xray_document_managment_system.Radiographer;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.xray_document_managment_system.Admin.Admin;
import org.example.xray_document_managment_system.Admin.AdminService;
import org.example.xray_document_managment_system.Hospital.Hospital;
import org.example.xray_document_managment_system.Hospital.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/radiographer")
public class RadiographerController {

    private final RadiographerService radiographerService;
    private final HospitalService hospitalService;
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Radiographer>> getAll(Principal principal) {
        Long hospitalId = getHospitalId(principal);
        List<Radiographer> radiographers = radiographerService.getAll();
        return ResponseEntity.ok(radiographers);
    }

    @PostMapping
    @Operation(description = "Create a radiographer under a hospital")
    public ResponseEntity<Radiographer> create(@RequestBody Radiographer radiographers, Principal principal) {

        Long hospitalId = getHospitalId(principal);
        Radiographer radiographer = radiographerService.create(radiographers,hospitalId);

        return ResponseEntity.ok(radiographer);
    }

    @GetMapping("/profile")
    @Operation(description = "Get radiographer profile")
    public ResponseEntity<Radiographer> create(Principal principal) {


        Radiographer radiographer = radiographerService.getByEmail(principal.getName());

        return ResponseEntity.ok(radiographer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Radiographer> getById(Long id) {
        Radiographer radiographer = radiographerService.getById(id);
        return ResponseEntity.ok(radiographer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Long id) {
        radiographerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    private Long getHospitalId(Principal principal){
        Optional<Admin> admin = adminService.findByEmail(principal.getName());
        if(admin.isEmpty()){
            throw new RuntimeException("Admin not found");
        }
        Optional<Hospital> hospital = hospitalService.getHospitalByAdmin(admin.get());
        if(hospital.isEmpty()){
            throw  new RuntimeException("Hospital not found");
        }
    return hospital.get().getId();
    }
}
