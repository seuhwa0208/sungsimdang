package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.FacilityStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityStatusRequest {

    @NotNull
    private FacilityStatus facilityStatus;
}
