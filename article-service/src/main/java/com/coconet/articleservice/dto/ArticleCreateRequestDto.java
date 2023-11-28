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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateRequestDto {
    private String title;
    private String content;
    private LocalDate plannedStartAt;
    private LocalDate expiredAt;
    private EstimatedDuration estimatedDuration;
    private ArticleType articleType;
    private MeetingType meetingType;
    private List<ArticleRoleDto> Roles = new ArrayList<>();
    private List<String> stacks = new ArrayList<>();
}