package com.coconet.chatservice.converter;

import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.dto.client.MemberResponse;
import com.coconet.chatservice.entity.ChatRoomEntity;
import lombok.Builder;

@Builder
public class ChatRoomEntityConverter {
    public static ChatroomResponseDto convertToDto (ChatRoomEntity chatRoomEntity,
                                                    MemberResponse writer,
                                                    MemberResponse applicant) {
        return ChatroomResponseDto.builder()
                .roomUUID(chatRoomEntity.getRoomUUID())
                .articleUUID(chatRoomEntity.getArticleUUID())
                .applicantUUID(chatRoomEntity.getApplicantUUID())
                .writerUUID(chatRoomEntity.getWriterUUID())
                .roomName(chatRoomEntity.getRoomName())
                .writerName(writer.getName())
                .writerImg(writer.getProfileImage())
                .applicantName(applicant.getName())
                .applicantImg(applicant.getProfileImage())
                .createdAt(chatRoomEntity.getCreatedAt())
                .updatedAt(chatRoomEntity.getUpdatedAt())
                .build();
    }

}