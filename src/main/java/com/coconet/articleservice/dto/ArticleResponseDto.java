package com.coconet.articleservice.dto;

import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private UUID articleUUID;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime plannedStartAt;
    private LocalDateTime expiredAt;
    private EstimatedDuration estimatedDuration;
    private int viewCount;
    private int bookmarkCount;
    private ArticleType articleType;
    private Byte status;
    private MeetingType meetingType;
    private UUID memberUUID;
    private boolean bookmarked;
    private String writerName;
    private String writerProfileImage;

    private List<ArticleRoleDto> roles = new ArrayList<>();
    private List<String> stacks = new ArrayList<>();
    private List<CommentResponseDto> comments = new ArrayList<>();
}