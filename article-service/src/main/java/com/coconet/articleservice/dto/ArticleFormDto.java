package com.coconet.articleservice.dto;

import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ArticleFormDto {
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
    private List<ArticleRoleDto> articleRoleDtos = new ArrayList<>();
    private List<ArticleStackDto> articleStackDtos = new ArrayList<>();
    private List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();

    @QueryProjection
    public ArticleFormDto(UUID articleUUID, String title, String content,
                          LocalDateTime createdAt, LocalDateTime updateAt,
                          LocalDateTime plannedStartAt, LocalDateTime expiredAt,
                          EstimatedDuration estimatedDuration, int viewCount, int bookmarkCount,
                          ArticleType articleType, Byte status, MeetingType meetingType, UUID memberUUID) {
        this.articleUUID = articleUUID;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.plannedStartAt = plannedStartAt;
        this.expiredAt = expiredAt;
        this.estimatedDuration = estimatedDuration;
        this.viewCount = viewCount;
        this.bookmarkCount = bookmarkCount;
        this.articleType = articleType;
        this.status = status;
        this.meetingType = meetingType;
        this.memberUUID = memberUUID;
    }
}
