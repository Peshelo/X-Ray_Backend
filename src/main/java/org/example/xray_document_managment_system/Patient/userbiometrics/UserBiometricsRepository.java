package org.example.xray_document_managment_system.Patient.userbiometrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author Terrance Nyamfukudza
 * 5/12/2023
 */
public interface UserBiometricsRepository extends JpaRepository<UserBiometrics, BigInteger> {
    UserBiometrics findByPrimaryFingerprint(byte[] primaryFinger);
    Boolean existsByPrimaryFingerprint(byte[] data);

    @Query(value = "SELECT e FROM UserBiometrics e WHERE e.primaryFingerprint = :blobData")
    Optional<UserBiometrics> findFingerprint(@Param("blobData") byte[] blobData);

}
