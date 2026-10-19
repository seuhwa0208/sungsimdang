package com.sungsimdang.project.board.dto;

<<<<<<< HEAD
=======

>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
import com.sungsimdang.project.board.domain.DataFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DataFilesResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DataFilesResponse from(DataFiles dataFiles) {
        return DataFilesResponse.builder()
                .id(dataFiles.getId())
                .title(dataFiles.getTitle())
                .content(dataFiles.getContent())
                .author(dataFiles.getMember().getLoginId())
                .createdAt(dataFiles.getCreatedAt())
                .updatedAt(dataFiles.getUpdatedAt())
                .build();
    }
}
