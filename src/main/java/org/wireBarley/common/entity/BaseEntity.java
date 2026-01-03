package org.wireBarley.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @CreatedBy
    @Column(updatable = false, columnDefinition = "BINARY(16)")
    private UUID createdBy;

    @LastModifiedBy
    @Column(columnDefinition = "BINARY(16)")
    private UUID updatedBy;
}