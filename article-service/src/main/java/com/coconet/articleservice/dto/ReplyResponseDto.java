package com.coconet.articleservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ReplyResponseDto {

    private Long replyId;
    private String content;
    private LocalDateTime repliedAt;
    private LocalDateTime updatedAt;
    private String author;
    private String articleUUID;

    public ReplyResponseDto(Long replyId, String content, LocalDateTime repliedAt, LocalDateTime updatedAt, String author) {
        this.replyId = replyId;
        this.content = content;
        this.repliedAt = repliedAt;
        this.updatedAt = updatedAt;
        this.author = author;
    }
}
