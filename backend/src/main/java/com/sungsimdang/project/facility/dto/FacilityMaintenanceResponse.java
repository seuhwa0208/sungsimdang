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
    private Long facilityId;
    private LocalDateTime createdAt;
    private FacilityStatus facilityStatus;
    private FacilityFaultType faultType;
    private String memberLoginId;

    public static FacilityMaintenanceResponse from(FacilityMaintenance facilityMaintenance) {
        return FacilityMaintenanceResponse.builder()
                .id(facilityMaintenance.getId())
                .facilityId(facilityMaintenance.getFacility().getId())
                .createdAt(facilityMaintenance.getCreatedAt())
                .facilityStatus(facilityMaintenance.getFacilityStatus())
                .faultType(facilityMaintenance.getFacilityFaultType())
                .memberLoginId(facilityMaintenance.getMember().getLoginId())
                .build();
    }
}
