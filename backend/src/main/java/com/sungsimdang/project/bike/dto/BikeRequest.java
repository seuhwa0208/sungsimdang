package com.sungsimdang.project.bike.dto;

import com.sungsimdang.project.bike.domain.BikeLocation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeRequest {

    @NotNull
    private BikeLocation location;
}
