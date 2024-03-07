package com.coconet.chatservice.converter;

import com.coconet.chatservice.client.ArticleClient;
import com.coconet.chatservice.client.MemberClient;
import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.dto.client.ArticleResponse;
import com.coconet.chatservice.dto.client.MemberResponse;
import com.coconet.chatservice.entity.ChatRoomEntity;
import com.coconet.chatservice.service.ChatRoomSubService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Component
public class ChatRoomEntityConverter {
    private static ArticleClient articleClient;
    private static MemberClient memberClient;
    private static ChatRoomSubService chatRoomSubService;

    @Autowired
    public ChatRoomEntityConverter(ArticleClient articleClient, MemberClient memberClient, ChatRoomSubService chatRoomSubService) {
        this.articleClient = articleClient;
        this.memberClient = memberClient;
        this.chatRoomSubService = chatRoomSubService;
    }

    public static ChatroomResponseDto convertToDto (ChatRoomEntity chatRoomEntity) {
        ArticleResponse articleResponse = articleClient.sendChatClient(chatRoomEntity.getArticleUUID()).getData();
        ArticleDto articleDto = ArticleDto.builder()
                                .articleUUID(articleResponse.getArticleUUID())
                                .name(articleResponse.getRoomName())
                                .build();

        return ChatroomResponseDto.builder()
                .roomUUID(chatRoomEntity.getRoomUUID())
                .article(articleDto)
                .opponentMember(null)
                .roomName(chatRoomEntity.getRoomName())
                .createdAt(chatRoomEntity.getCreatedAt())
                .updatedAt(chatRoomEntity.getUpdatedAt())
                .build();
    }

    public static ChatroomResponseDto convertToDto (ChatRoomEntity chatRoomEntity, UUID memberUUID) {
        ArticleResponse articleResponse = articleClient.sendChatClient(chatRoomEntity.getArticleUUID()).getData();
        ArticleDto articleDto = ArticleDto.builder()
                .articleUUID(articleResponse.getArticleUUID())
                .name(articleResponse.getRoomName())
                .build();

        UUID opponentUUID = chatRoomSubService.getOpponentUUID(memberUUID, chatRoomEntity.getRoomUUID());
        MemberResponse memberResponse = memberClient.sendChatClient(opponentUUID).getData();
        MemberDto memberDto = MemberDto.builder()
                .name(memberResponse.getName())
                .image(memberResponse.getProfileImage())
                .build();

        return ChatroomResponseDto.builder()
                .roomUUID(chatRoomEntity.getRoomUUID())
                .article(articleDto)
                .opponentMember(memberDto)
                .roomName(chatRoomEntity.getRoomName())
                .createdAt(chatRoomEntity.getCreatedAt())
                .updatedAt(chatRoomEntity.getUpdatedAt())
                .build();

    }

}