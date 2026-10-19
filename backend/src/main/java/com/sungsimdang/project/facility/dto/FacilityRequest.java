package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.FacilityLocation;
import com.sungsimdang.project.facility.domain.FacilityType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityRequest {

    @NotNull
    private FacilityLocation location;

    @NotNull
    private FacilityType facilityType;
}
