package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityLocation;
import com.sungsimdang.project.facility.domain.FacilityStatus;
import com.sungsimdang.project.facility.domain.FacilityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FacilityResponse {

    private Long id;
    private FacilityLocation location;
    private FacilityType facilityType;
    private FacilityStatus facilityStatus;

    public static FacilityResponse from(Facility facility) {
        return FacilityResponse.builder()
                .id(facility.getId())
                .location(facility.getLocation())
                .facilityType(facility.getFacilityType())
                .facilityStatus(facility.getFacilityStatus())
                .build();
    }
}
