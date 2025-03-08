package org.example.xray_document_managment_system.Patient.userbiometrics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Patient.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

/**
 * @author Terrance Nyamfukudza
 * 8/12/2023
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user-biometrics")
@Tag(name = "Finger print encoded image", description = "Finger print encoded image")
@CrossOrigin
public class UserBiometricsController {
    private final PatientService patientService;
    @PostMapping("/encoded-file")
    @Operation(description = "Returns user details by primary finger")
    public ResponseEntity<?> getByEncoded(@RequestBody BioMetricData bioMetricData) throws IOException {
        return ResponseEntity.ok(patientService.getByEncodedImage(bioMetricData));
    }
}
