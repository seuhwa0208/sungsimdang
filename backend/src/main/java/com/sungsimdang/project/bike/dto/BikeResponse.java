package com.sungsimdang.project.bike.dto;

import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.domain.BikeLocation;
import com.sungsimdang.project.bike.domain.BikeStatus;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.dto.MemberResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BikeResponse {

    private Long id;
    private BikeLocation location;
    private BikeStatus status;
    private float distance;

    public static BikeResponse from(Bike bike) {
        return BikeResponse.builder()
                .id(bike.getId())
                .location(bike.getLocation())
                .status(bike.getStatus())
                .distance(bike.getDistance())
                .build();
    }
}
