package com.sungsimdang.project.bike.repository;

import com.sungsimdang.project.bike.domain.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

}
