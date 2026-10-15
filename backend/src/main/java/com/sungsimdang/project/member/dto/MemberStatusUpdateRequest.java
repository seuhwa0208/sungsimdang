package com.sungsimdang.project.member.dto;

import com.sungsimdang.project.member.domain.MemberStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberStatusUpdateRequest {

    @NotNull
    private MemberStatus status;
}
