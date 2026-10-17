package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.FacilityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityRequest {

    @NotBlank
    private String location;

    @NotNull
    private FacilityType facilityType;
}
