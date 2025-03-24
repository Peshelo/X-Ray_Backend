package org.example.xray_document_managment_system.Patient;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Patient.userbiometrics.BioMetricData;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@Data
@RequestMapping("/patient")
@Tag(name = "Patient Managment", description = "Manage patients")
public class PatientController {

        private final PatientService patientService;

        @GetMapping
        public ResponseEntity<List<Patient>> getPatient() {
            return ResponseEntity.ok().body(patientService.getAll());
        }
        


        @GetMapping("/{id}")
        public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
            return ResponseEntity.ok().body(patientService.getById(id));
        }

    @GetMapping("/profile")
    public ResponseEntity<Patient> getPatientProfile(Principal principal) {
            String email = principal.getName();
        return ResponseEntity.ok().body(patientService.getByEmail(email));
    }

    @GetMapping("/gey-by-nationalId/{nationalId}")
    public ResponseEntity<Patient> getPatientByNaitonalId(@PathVariable String nationalId) {
        return ResponseEntity.ok().body(patientService.getByNationalId(nationalId));
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
            patientService.delete(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDto) {
            return ResponseEntity.ok().body(patientService.update(id, patientDto));
        }

        @PostMapping
        public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
            return ResponseEntity.ok().body(patientService.create(patient));
        }


    @PostMapping(path = "/add-fingerprint",consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Adds a finger print")
    public ResponseEntity<?> saveImage(@RequestParam("files") MultipartFile file, @RequestParam("userId") Long bioId) throws IOException {
        byte[] primaryFinger = file.getBytes();
        return ResponseEntity.ok(patientService.addFingerPrint(primaryFinger,bioId));
    }

    @PostMapping(path = "/get-by-fingerprint", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Returns user details by primary finger")
    public ResponseEntity<?> getByPrimaryFinger(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(patientService.getByPrimaryFinger(file));
    }

    @PostMapping("/encoded-file")
    @Operation(description = "Returns user details by primary finger")
    public ResponseEntity<?> getByEncoded(@RequestBody BioMetricData bioMetricData) throws IOException {
        return ResponseEntity.ok(patientService.getByEncodedImage(bioMetricData));
    }

}
