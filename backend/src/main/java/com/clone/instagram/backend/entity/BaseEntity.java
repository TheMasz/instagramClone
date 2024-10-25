package com.clone.instagram.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
}
