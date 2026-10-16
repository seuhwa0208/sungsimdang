package com.sungsimdang.project.facility.restcontroller;

import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import com.sungsimdang.project.facility.dto.FacilityResponse;
import com.sungsimdang.project.facility.repository.FacilityMaintenanceRepository;
import com.sungsimdang.project.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/facilities")
public class FacilityRestController {
    private final FacilityMaintenanceRepository facilityMaintenance;
    private final FacilityRepository facility;

    // 시설 목록
    @GetMapping
    public List<FacilityResponse> list(){
        return facility.
    }

    // 시설 상세

    // 시설 등록

    // 시설 수정

    // 시설 삭제

    //
}
