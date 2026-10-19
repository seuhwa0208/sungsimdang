package com.sungsimdang.project.bike.dto;

import com.sungsimdang.project.bike.domain.BikeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeStatusRequest {

    @NotNull
    private BikeStatus status;
}
