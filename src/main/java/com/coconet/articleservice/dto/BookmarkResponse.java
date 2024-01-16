package com.coconet.articleservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
public class BookmarkResponse {
    private String title;
    private UUID articleUUID;
    private LocalDateTime plannedStartAt;
    private LocalDateTime expiredAt;
}
