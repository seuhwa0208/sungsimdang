package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.Facility;
<<<<<<< HEAD
=======
import com.sungsimdang.project.facility.domain.FacilityLocation;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
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
<<<<<<< HEAD
    private String location;
=======
    private FacilityLocation location;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
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
