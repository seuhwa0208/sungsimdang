package com.sungsimdang.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
