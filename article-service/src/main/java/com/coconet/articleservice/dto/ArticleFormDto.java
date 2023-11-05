package com.coconet.articleservice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleFormDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime expiredAt;
    private int viewCount;
    private int bookmarkCount;
    private String articleType;
    private Byte status;
    private String meetingType;
    private String author;
    private List<ArticleRoleDto> articleRoleDtos = new ArrayList<>();
    private List<ArticleStackDto> articleStackDtos = new ArrayList<>();

    @QueryProjection
    public ArticleFormDto(String title, String content, LocalDateTime createdAt,
                          LocalDateTime updateAt, LocalDateTime expiredAt,
                          int viewCount, int bookmarkCount,
                          String articleType, Byte status,
                          String meetingType, String author) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.expiredAt = expiredAt;
        this.viewCount = viewCount;
        this.bookmarkCount = bookmarkCount;
        this.articleType = articleType;
        this.status = status;
        this.meetingType = meetingType;
        this.author = author;
    }
}
