package com.sungsimdang.project.board.dto;


import com.sungsimdang.project.board.domain.Qna;
import com.sungsimdang.project.board.domain.QnaStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class QnaResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String answer;
    private QnaStatus status;
    private long hits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime answeredAt;

    public static QnaResponse from(Qna qna) {
        return QnaResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .author(qna.getMember().getLoginId())
                .answer(qna.getAnswer())
                .status(qna.getStatus())
                .hits(qna.getHits())
                .createdAt(qna.getCreatedAt())
                .updatedAt(qna.getUpdatedAt())
                .answeredAt(qna.getAnsweredAt())
                .build();
    }
}

