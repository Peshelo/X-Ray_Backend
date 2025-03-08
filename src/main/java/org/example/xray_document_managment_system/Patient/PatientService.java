package org.example.xray_document_managment_system.Patient;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.xray_document_managment_system.Patient.userbiometrics.BioMetricData;
import org.example.xray_document_managment_system.Patient.userbiometrics.UserBiometrics;
import org.example.xray_document_managment_system.Patient.userbiometrics.UserBiometricsRepository;
import org.example.xray_document_managment_system.Patient.userbiometrics.UserBiometricsService;
import org.example.xray_document_managment_system.Security.Models.Role;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.Pageable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
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
//            Patient patient = patientRepository.findById(patientId).get();
//
//            UserBiometrics userBiometrics = UserBiometrics.builder()
//                    .primaryFingerprint(primaryFinger)
//                    .build();
//
//            patient.setUserBiometrics(userBiometrics);
//            Patient savePatient = patientRepository.save(patient);
//            userBiometricsRepository.save(userBiometrics);
//            return savePatient;
        return null;
        }

    public Patient getByEncodedImage(BioMetricData bioMetricData) {
//        double threshold = 40;
//        BufferedImage decodedImage=null;
//        ByteArrayOutputStream baos= null;
//        try {
//            decodedImage= decodeBase64Bmp(bioMetricData.getData());
//            baos = new ByteArrayOutputStream();
//            ImageIO.write(decodedImage, "jpg", baos);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        byte[] bytes = baos.toByteArray();
//        List<UserDetailsDto> resultList = repository.findAll().stream()
//                .filter(print -> {
//                    FingerprintTemplate probe = new FingerprintTemplate().dpi(500).create(bytes);
//                    FingerprintTemplate candidate = new FingerprintTemplate().dpi(500)
//                            .create(print.getPrimaryFingerprint());
//                    double score = new FingerprintMatcher().index(probe).match(candidate);
//                    return score >= threshold;
//                })
//                .map(print -> {
//                    var userDetails = userDetailsRepository.findById(print.getUserId());
//                    UserDetailsDto userDetailsDto = new UserDetailsDto();
//                    userDetails.ifPresent(user -> {
//                        userDetailsDto.setId(user.getId());
//                        userDetailsDto.setName(user.getName());
//                        userDetailsDto.setSurname(user.getSurname());
//                        userDetailsDto.setStatus(user.getStatus());
//                        userDetailsDto.setNationalId(user.getNatId());
//                    });
//                    return userDetailsDto;
//                })
//                .collect(Collectors.toList());

        return null;
    }

    public Patient getByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()){
            throw new RuntimeException("Patient not found");
        }
        return patient.get();
    }


}
