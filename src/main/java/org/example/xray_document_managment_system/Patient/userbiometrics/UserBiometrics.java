package org.example.xray_document_managment_system.Patient.userbiometrics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserBiometrics {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Basic
    private Long userId;
    @Basic
    @Lob
    private byte[] fingerprint1;
    @Basic
    @Lob
    private byte[] fingerprint2;
    @Basic
    @Lob
    private byte[] fingerprint3;
    @Basic
    @Lob
    private byte[] fingerprint4;
    @Basic
    @Lob
    private byte[] fingerprint5;
    @Basic
    @Lob
    private byte[] fingerprint6;
    @Basic
    @Lob
    private byte[] fingerprint7;
    @Basic
    @Lob
    private byte[] fingerprint8;
    @Basic
    @Lob
    private byte[] fingerprint9;
    @Basic
    @Lob
    private byte[] fingerprint10;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] primaryFingerprint;

}
