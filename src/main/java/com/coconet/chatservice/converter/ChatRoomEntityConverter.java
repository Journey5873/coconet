package com.coconet.chatservice.converter;

import com.coconet.chatservice.dto.RoomResponseDto;
import com.coconet.chatservice.entity.ChatRoomEntity;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class ChatRoomEntityConverter {
    public static RoomResponseDto convertToDto (ChatRoomEntity chatRoomEntity) {
        return RoomResponseDto.builder()
                .roomUUID(chatRoomEntity.getRoomUUID())
                .articleUUID(chatRoomEntity.getArticleUUID())
                .applicantUUID(chatRoomEntity.getApplicantUUID())
                .roomName(chatRoomEntity.getRoomName())
                .createdAt(chatRoomEntity.getCreatedAt())
                .updatedAt(chatRoomEntity.getUpdatedAt())
                .build();
    }

    public static RoomResponseDto convertToDtoWithMembers (ChatRoomEntity chatRoomEntity, List<UUID> members) {
        return RoomResponseDto.builder()
                .roomUUID(chatRoomEntity.getRoomUUID())
                .articleUUID(chatRoomEntity.getArticleUUID())
                .members(members)
//                .members(chatRoomEntity.getRoomMembers().stream().map(m -> m.getMemberUUID()).toList())
                .roomName(chatRoomEntity.getRoomName())
                .createdAt(chatRoomEntity.getCreatedAt())
                .updatedAt(chatRoomEntity.getUpdatedAt())
                .build();
    }

}