package com.sungsimdang.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaAnswerRequest {

    @NotBlank
    private String answer;
}
