package com.sungsimdang.project.facility.repository;

import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import com.sungsimdang.project.facility.domain.FacilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityMaintenanceRepository extends JpaRepository<FacilityMaintenance, Long> {
    List<FacilityMaintenance> findAllByOrderByCreatedAtDesc();
<<<<<<< HEAD
    Optional<FacilityMaintenance> findByFacility_IdAndFacilityStatusOrderByCreatedAtDesc(Long facilityId, FacilityStatus facilityStatus);
=======
    Optional<FacilityMaintenance> findFirstByFacility_IdAndFacilityStatusOrderByCreatedAtDesc(Long facilityId, FacilityStatus facilityStatus);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
}
