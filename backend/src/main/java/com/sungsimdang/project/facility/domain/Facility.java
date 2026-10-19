package com.sungsimdang.project.facility.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "facility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
//    @Column(nullable = false, unique = true, length = 100)
//    private String facilityNo;

    @Column(nullable = false, length = 100)
    private String location;
=======
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private FacilityLocation location;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private FacilityType facilityType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private FacilityStatus facilityStatus = FacilityStatus.AVAILABLE;

    @Column(nullable = false, updatable = false, name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
