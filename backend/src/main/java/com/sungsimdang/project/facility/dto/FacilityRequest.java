package com.sungsimdang.project.facility.dto;

<<<<<<< HEAD
import com.sungsimdang.project.facility.domain.FacilityType;
import jakarta.validation.constraints.NotBlank;
=======
import com.sungsimdang.project.facility.domain.FacilityLocation;
import com.sungsimdang.project.facility.domain.FacilityType;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityRequest {

<<<<<<< HEAD
    @NotBlank
    private String location;
=======
    @NotNull
    private FacilityLocation location;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392

    @NotNull
    private FacilityType facilityType;
}
