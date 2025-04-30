package org.example.xray_document_managment_system.Radiograph;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Radiographer.Radiographer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/radiograph")
@Tag(name = "Radiograph Records Managment", description = "Manage records of all radiographs")
public class RadiographController {

    private final RadiographService radiographService;

    @GetMapping
    public ResponseEntity<List<Radiograph>> getAll() {
        List<Radiograph> radiographs = radiographService.getAll();
        return ResponseEntity.ok(radiographs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Radiograph> getById(@PathVariable Long id) {
        Radiograph radiograph = radiographService.getById(id);
        return ResponseEntity.ok(radiograph);
    }
    @GetMapping("/get-by-patient/{id}")
    public ResponseEntity<List<Radiograph>> getByPatientId(@PathVariable Long id) {
        List<Radiograph> radiographs = radiographService.getAllByPatientId(id);
        return ResponseEntity.ok(radiographs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        radiographService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody RadiographDTO radiographDTO) {
        radiographService.editById(id,radiographDTO.getDescription());
        return ResponseEntity.noContent().build();
    }

//    @PostMapping
//    public ResponseEntity<Radiograph> create(Radiograph radiograph) {
//        Radiograph createdRadiograph = radiographService.create(radiograph);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdRadiograph);
//    }

    @PostMapping("/create-for-patient/{id}")
    @Operation(description = "Create a radiograph under a patient")
    public ResponseEntity<Radiograph> create(@RequestBody Radiograph radiographers,@PathVariable Long id,Principal principal) {

        String radiographerEmail = principal.getName();
        Radiograph radiograph = radiographService.create(radiographers,id,radiographerEmail);

        return ResponseEntity.ok(radiograph);
    }
}
