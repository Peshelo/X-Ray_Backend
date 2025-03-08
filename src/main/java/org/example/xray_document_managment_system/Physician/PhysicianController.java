package org.example.xray_document_managment_system.Physician;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Radiographer.Radiographer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/physician")
@Tag(name = "Physician Managment", description = "Manage physicians")
public class PhysicianController {

    private final PhysicianService physicianService;

    @GetMapping
    public List<Physician> getAll() {
        return physicianService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Physician> getById(Long id) {
        Physician physician = physicianService.getById(id);
        return ResponseEntity.ok(physician);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Long id) {
        physicianService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    @Operation(description = "Get physician profile")
    public ResponseEntity<Physician> create(Principal principal) {


        Physician physician = physicianService.getByEmail(principal.getName());

        return ResponseEntity.ok(physician);
    }


    @PostMapping
    public ResponseEntity<Physician> create(@RequestBody Physician physician) {
        Physician createdPhysician = physicianService.create(physician);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhysician);
    }
}
