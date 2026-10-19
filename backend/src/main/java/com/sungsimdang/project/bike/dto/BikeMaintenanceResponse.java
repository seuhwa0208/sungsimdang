package com.sungsimdang.project.bike.dto;

import com.sungsimdang.project.bike.domain.BikeFaultType;
import com.sungsimdang.project.bike.domain.BikeMaintenance;
import com.sungsimdang.project.bike.domain.BikeStatus;
import com.sungsimdang.project.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BikeMaintenanceResponse {

    private Long id;
    private Long bikeId;
    private LocalDateTime createdAt;
    private BikeStatus status;
    private BikeFaultType faultType;
    private String memberLoginId;

    public static BikeMaintenanceResponse from(BikeMaintenance bikeMaintenance) {
        return BikeMaintenanceResponse.builder()
                .id(bikeMaintenance.getId())
                .bikeId(bikeMaintenance.getBike().getId())
                .createdAt(bikeMaintenance.getCreatedAt())
                .status(bikeMaintenance.getStatus())
                .faultType(bikeMaintenance.getBikeFaultType())
                .memberLoginId(bikeMaintenance.getMember().getLoginId())
                .build();
    }
}
