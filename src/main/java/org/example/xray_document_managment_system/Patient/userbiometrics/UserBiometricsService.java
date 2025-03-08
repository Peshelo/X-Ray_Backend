package org.example.xray_document_managment_system.Patient.userbiometrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.math.BigInteger;

/**
 * @author Terrance Nyamfukudza
 * 5/12/2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserBiometricsService {
    private final UserBiometricsRepository repository;

    public UserBiometrics create(byte[] primaryFinger,BigInteger bioId){
        var bio = repository.findById(bioId);
        UserBiometrics biometrics = bio.get();
        biometrics.setPrimaryFingerprint(primaryFinger);
        biometrics = repository.save(biometrics);
        return biometrics;
    }
}
