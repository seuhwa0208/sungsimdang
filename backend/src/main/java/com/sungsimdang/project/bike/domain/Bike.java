package com.sungsimdang.project.bike.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bike")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bike {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String bikeNo;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(nullable = false)
    @Builder.Default
    private float distance = 0f;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private BikeStatus status = BikeStatus.AVAILABLE;

    @Column(nullable = false, name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if(createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
