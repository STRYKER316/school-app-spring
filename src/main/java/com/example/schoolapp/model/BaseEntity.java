package com.example.schoolapp.model;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
