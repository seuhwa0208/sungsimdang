package com.sungsimdang.project.facility.dto;

import com.sungsimdang.project.facility.domain.FacilityFaultType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityFaultRequest {

    @NotNull
    private FacilityFaultType faultType;
}
