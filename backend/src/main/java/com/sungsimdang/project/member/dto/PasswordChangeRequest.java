package com.sungsimdang.project.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 4, max = 30)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]+$", message = "4자~30자, 영문과 숫자, 1개 이상의 특수문자를 사용해야 합니다.")
    private String newPassword;

    @NotBlank
    private String passwordCheck;
}
