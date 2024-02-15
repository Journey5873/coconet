package com.coconet.articleservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CommentResponseDto {
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID commentUUID;
    private UUID memberUUID;
    private UUID articleUUID;
    private String writerName;
    private String writerProfileImage;
}