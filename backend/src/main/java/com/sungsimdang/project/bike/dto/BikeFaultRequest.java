package com.sungsimdang.project.bike.dto;

import com.sungsimdang.project.bike.domain.BikeFaultType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeFaultRequest {

    @NotNull
    private BikeFaultType faultType;
}
