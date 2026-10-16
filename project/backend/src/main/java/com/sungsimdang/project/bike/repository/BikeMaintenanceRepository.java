package com.sungsimdang.project.bike.repository;

import com.sungsimdang.project.bike.domain.BikeMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeMaintenanceRepository extends JpaRepository<BikeMaintenance, Long> {
    List<BikeMaintenance> findAllByOrderByCreatedAtDesc();
}
