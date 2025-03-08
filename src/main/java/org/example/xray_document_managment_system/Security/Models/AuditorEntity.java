package org.example.xray_document_managment_system.Security.Models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private Long createdByUserId;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private Long updatedByUserId;

}