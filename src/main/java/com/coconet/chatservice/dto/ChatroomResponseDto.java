package com.coconet.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatroomResponseDto {
    private UUID roomUUID;
    private UUID articleUUID;
    private UUID applicantUUID;
    private UUID writerUUID;
    private String roomName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
