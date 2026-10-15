package com.sungsimdang.project.board.dto;

import com.sungsimdang.project.board.domain.QnaStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QnaResponse {

    private Long id;

    private String title;

    private String content;

    private String answer;

    private QnaStatus status;

    private long hits;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime answeredAt;
}
