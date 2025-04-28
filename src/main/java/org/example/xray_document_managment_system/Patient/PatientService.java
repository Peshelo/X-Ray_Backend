package org.example.xray_document_managment_system.Patient;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.xray_document_managment_system.Patient.userbiometrics.BioMetricData;
import org.example.xray_document_managment_system.Patient.userbiometrics.UserBiometrics;
import org.example.xray_document_managment_system.Patient.userbiometrics.UserBiometricsRepository;
import org.example.xray_document_managment_system.Security.Models.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBiometricsRepository userBiometricsRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient create(Patient patient) {
        patient.setRoles(List.of(Role.PATIENT));
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }

    public Patient update(Long id, PatientDTO patientDto) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return null;
        }
        patient.setFingerprintData(patientDto.getFingerprintData());
//        patient.setLanguage(patientDto.getLanguage());
        return patientRepository.save(patient);
    }

    public void delete(Long id) {
        boolean exists = patientRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    public Patient getByNationalId(String nationalId) {
        Optional<Patient> patient =  patientRepository.findByNationalId(nationalId);
        if(patient.isEmpty()){
            throw new RuntimeException("Record not found");
        }
        return patient.get();
    }

    public Patient addFingerPrint(byte[] primaryFinger, Long patientId) {
            Patient patient = patientRepository.findById(patientId).get();

            UserBiometrics userBiometrics = UserBiometrics.builder()
                    .primaryFingerprint(primaryFinger)
                    .userId(patientId)
                    .build();
            if(patient.getUserBiometrics().getPrimaryFingerprint() != null){
                throw new RuntimeException("User already has fingerprint");
            }

       UserBiometrics savedUserBiometrics  = userBiometricsRepository.save(userBiometrics);
        patient.setUserBiometrics(savedUserBiometrics);
        return patientRepository.save(patient);
        }

    public Patient getByEncodedImage(BioMetricData bioMetricData) {
        double threshold = 40;
        BufferedImage decodedImage=null;
        ByteArrayOutputStream baos= null;
        try {
            decodedImage= decodeBase64Bmp(bioMetricData.getData());
            baos = new ByteArrayOutputStream();
            ImageIO.write(decodedImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = baos.toByteArray();
       List<Optional<Patient>> resultList = userBiometricsRepository.findAll().stream()
                .filter(print -> {
                    FingerprintTemplate probe = new FingerprintTemplate().dpi(500).create(bytes);
                    FingerprintTemplate candidate = new FingerprintTemplate().dpi(500)
                            .create(print.getPrimaryFingerprint());
                    double score = new FingerprintMatcher().index(probe).match(candidate);
                    return score >= threshold;
                })
                .map(print -> {
//                    var userDetails = patientRepository.findById(print.getUserId());
//
                    return patientRepository.findById(print.getId());
                })
                .collect(Collectors.toList());

        return (Patient) resultList;
    }

    public Patient getByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()){
            throw new RuntimeException("Patient not found");
        }
        return patient.get();
    }


    public static BufferedImage decodeBase64Bmp(String base64String) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        BufferedImage image = ImageIO.read(inputStream);

        return image;
    }

    public Optional<Patient> getByPrimaryFinger(MultipartFile file) throws IOException {
        double threshold = 40;
        byte[] encoded = file.getBytes();

        List<Optional<Patient>> resultList = userBiometricsRepository.findAll().stream()
                .filter(print -> {
                    FingerprintTemplate probe = new FingerprintTemplate().dpi(500).create(encoded);
                    FingerprintTemplate candidate = new FingerprintTemplate().dpi(500)
                            .create(print.getPrimaryFingerprint());
                    double score = new FingerprintMatcher().index(probe).match(candidate);
                    return score >= threshold;
                })
                .map(print -> patientRepository.findById(print.getUserId()))
                .collect(Collectors.toList());

        return resultList.get(0);
    }

}
