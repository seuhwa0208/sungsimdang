package com.sungsimdang.project.member.dto;

import com.sungsimdang.project.member.domain.MemberRole;
import com.sungsimdang.project.member.domain.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequest {

    private String name;

    @Email
    private String email;

    private String tel;
}