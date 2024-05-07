package com.example.schoolapp.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@MappedSuperclass
public class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
