package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.FacilityFaultType;
import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import com.sungsimdang.project.facility.domain.FacilityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FacilityMaintenanceResponse {

    private Long id;
<<<<<<< HEAD
=======
    private Long facilityId;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
    private LocalDateTime createdAt;
    private FacilityStatus facilityStatus;
    private FacilityFaultType faultType;
    private String memberLoginId;

    public static FacilityMaintenanceResponse from(FacilityMaintenance facilityMaintenance) {
        return FacilityMaintenanceResponse.builder()
                .id(facilityMaintenance.getId())
<<<<<<< HEAD
=======
                .facilityId(facilityMaintenance.getFacility().getId())
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
                .createdAt(facilityMaintenance.getCreatedAt())
                .facilityStatus(facilityMaintenance.getFacilityStatus())
                .faultType(facilityMaintenance.getFacilityFaultType())
                .memberLoginId(facilityMaintenance.getMember().getLoginId())
                .build();
    }
}
