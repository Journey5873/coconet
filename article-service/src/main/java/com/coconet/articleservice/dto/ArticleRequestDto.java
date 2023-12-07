package com.coconet.articleservice.dto;

import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ArticleRequestDto {
    private UUID articleUUID;
    private String title;
    private String content;
    private LocalDate plannedStartAt;
    private LocalDate expiredAt;
    private EstimatedDuration estimatedDuration;
    private ArticleType articleType;
    private MeetingType meetingType;
    private List<ArticleRoleDto> roles = new ArrayList<>();
    private List<ArticleStackDto> stacks = new ArrayList<>();
}