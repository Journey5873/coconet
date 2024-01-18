package com.coconet.articleservice.dto;

import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<ArticleRoleDto> roles = new ArrayList<>();
    private List<String> stacks = new ArrayList<>();

    public ArticleEntity toEntity() {
        return ArticleEntity.builder()
                .title(this.title)
                .content(this.content)
                .plannedStartAt(this.plannedStartAt.atTime(LocalTime.MAX))
                .expiredAt(this.expiredAt.atTime(LocalTime.MAX))
                .estimatedDuration(this.estimatedDuration.name())
                .viewCount(0)
                .bookmarkCount(0)
                .articleType(this.articleType.name())
                .status((byte) 1)
                .meetingType(this.meetingType.name())
                .build();
    }

    public ArticleEntity toEntity(UUID memberUUID) {
        return ArticleEntity.builder()
                .title(this.title)
                .articleUUID(UUID.randomUUID())
                .memberUUID(memberUUID)
                .content(this.content)
                .plannedStartAt(this.plannedStartAt.atTime(LocalTime.MAX))
                .expiredAt(this.expiredAt.atTime(LocalTime.MAX))
                .estimatedDuration(this.estimatedDuration.name())
                .viewCount(0)
                .bookmarkCount(0)
                .articleType(this.articleType.name())
                .status((byte) 1)
                .meetingType(this.meetingType.name())
                .build();
    }

}