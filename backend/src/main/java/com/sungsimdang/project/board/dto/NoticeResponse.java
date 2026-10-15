package com.sungsimdang.project.board.dto;

import com.sungsimdang.project.board.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NoticeResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .author(notice.getMember().getLoginId())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }
}
