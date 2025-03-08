package org.example.xray_document_managment_system.Patient;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.xray_document_managment_system.Security.Models.Gender;
import org.example.xray_document_managment_system.Security.Models.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {

    private String firstname;

    private String lastname;

    private String email;

    private String address;;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    private String mobileNumber;

    private String nationalId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String fingerprintData;

    private String language;

}
