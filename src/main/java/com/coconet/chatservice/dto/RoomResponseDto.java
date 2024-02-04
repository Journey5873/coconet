package com.coconet.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RoomResponseDto {
    private UUID roomUUID;
    private UUID articleUUID;
    private UUID applicantUUID;
    private List<UUID> members; // 대화방 참가자 리스트
    private String roomName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
