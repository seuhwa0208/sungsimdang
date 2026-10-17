package com.sungsimdang.project.facility.repository;

import com.sungsimdang.project.facility.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findAllByOrderByCreatedAtDesc();
    List<Facility> findAllByOrderByFacilityStatusDesc();
    //List<Facility> findByFacilityNoContainingOrderByCreatedAtDesc(String facilityNo);
    List<Facility> findByLocationOrderByCreatedAtDesc(String location);
}
