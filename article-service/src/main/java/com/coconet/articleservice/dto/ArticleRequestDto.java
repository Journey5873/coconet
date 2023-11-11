package com.coconet.articleservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate plannedStartAt;
    private LocalDate expiredAt;
    private String estimatedDuration;
    private String articleType;
    private String meetingType;

    private List<ArticleRoleDto> articleRoleDtos;
    private List<ArticleStackDto> articleStackDtos;
}
