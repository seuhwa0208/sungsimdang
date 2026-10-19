package com.sungsimdang.project.bike.repository;

import com.sungsimdang.project.bike.domain.BikeMaintenance;
import com.sungsimdang.project.bike.domain.BikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeMaintenanceRepository extends JpaRepository<BikeMaintenance, Long> {
    List<BikeMaintenance> findAllByOrderByCreatedAtDesc();
    Optional<BikeMaintenance> findFirstByBike_IdAndStatusOrderByCreatedAtDesc(Long id, BikeStatus status);
}
