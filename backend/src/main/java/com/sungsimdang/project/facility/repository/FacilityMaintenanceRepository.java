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
    Optional<FacilityMaintenance> findFirstByFacility_IdAndFacilityStatusOrderByCreatedAtDesc(Long facilityId, FacilityStatus facilityStatus);
    void deleteByFacility_Id (Long facilityId);
}
