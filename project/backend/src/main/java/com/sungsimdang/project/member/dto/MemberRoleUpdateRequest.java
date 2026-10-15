package com.sungsimdang.project.member.dto;

import com.sungsimdang.project.member.domain.MemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRoleUpdateRequest {

    @NotNull
    private MemberRole role;
}
