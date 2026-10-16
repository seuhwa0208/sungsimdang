package com.sungsimdang.project.bike.repository;

import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.dto.BikeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    List<Bike> findAllByOrderByCreatedAtDesc();
    List<Bike> findAllByOrderByBikeStatusDesc();
    List<Bike> findByLocationOrderByCreatedAtDesc();
    Optional<Bike> findByIdOrderByCreatedAtDesc(Long id);
}
