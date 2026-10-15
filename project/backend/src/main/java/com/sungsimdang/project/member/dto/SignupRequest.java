package com.sungsimdang.project.member.dto;

import com.sungsimdang.project.member.domain.MemberRole;
import com.sungsimdang.project.member.domain.MemberStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 4, max = 20, message = "최소 4자에서 최대 20자까지 입력해야 합니다.")
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 30)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]+$", message = "4자~30자, 영문과 숫자, 1개 이상의 특수문자를 사용해야 합니다.")
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String tel;

    @AssertTrue
    private boolean termsAgreed;

    @AssertTrue
    private boolean privacyAgreed;
}