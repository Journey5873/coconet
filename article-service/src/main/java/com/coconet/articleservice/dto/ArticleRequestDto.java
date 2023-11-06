package com.coconet.articleservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ArticleRequestDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime plannedStartAt;
    private LocalDateTime expiredAt;
    private String estimatedDuration;
    private int viewCount;
    private int bookmarkCount;
    private String articleType;
    private Byte status;
    private String meetingType;
    private String author;

    private List<ArticleRoleDto> articleRoleDtos;
    private List<ArticleStackDto> articleStackDtos;
}
