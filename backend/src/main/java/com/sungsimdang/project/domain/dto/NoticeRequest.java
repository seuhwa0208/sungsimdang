package com.sungsimdang.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
