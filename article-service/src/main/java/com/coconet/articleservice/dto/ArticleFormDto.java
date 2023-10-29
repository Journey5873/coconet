package com.coconet.articleservice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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


    @QueryProjection
    public ArticleFormDto(String title, String content,
                          LocalDateTime createdAt, LocalDateTime updateAt, LocalDateTime expiredAt,
                          int viewCount, int bookmarkCount, String articleType,
                          Byte status, String meetingType, String author) {
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
