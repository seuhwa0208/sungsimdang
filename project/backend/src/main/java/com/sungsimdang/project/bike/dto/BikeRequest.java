package com.sungsimdang.project.bike.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeRequest {

    @NotBlank
    private String location;
}
