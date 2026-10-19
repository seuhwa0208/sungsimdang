package com.sungsimdang.project.facility.repository;

import com.sungsimdang.project.facility.domain.Facility;
<<<<<<< HEAD
=======
import com.sungsimdang.project.facility.domain.FacilityLocation;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findAllByOrderByCreatedAtDesc();
    List<Facility> findAllByOrderByFacilityStatusDesc();
    //List<Facility> findByFacilityNoContainingOrderByCreatedAtDesc(String facilityNo);
<<<<<<< HEAD
    List<Facility> findByLocationOrderByCreatedAtDesc(String location);
=======
    List<Facility> findByLocationOrderByCreatedAtDesc(FacilityLocation location);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
}
