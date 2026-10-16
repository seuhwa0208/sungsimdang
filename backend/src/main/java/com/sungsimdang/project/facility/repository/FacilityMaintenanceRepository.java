package com.sungsimdang.project.facility.repository;

import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityMaintenanceRepository extends JpaRepository<FacilityMaintenance, Long> {
    List<FacilityMaintenance> findAllByOrderByCreatedAtDesc();
}
