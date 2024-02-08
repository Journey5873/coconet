package com.coconet.chatservice.converter;

import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.entity.ChatMsgEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public class ChatMsgEntityToConverter {

    public static ChatMsgResponseDto convertToDto(ChatMsgEntity chatMsgEntity) {
        return ChatMsgResponseDto.builder()
                .senderUUID(chatMsgEntity.getSenderUUID())
                .roomUUID(chatMsgEntity.getRoomUUID())
                .message(chatMsgEntity.getMessage())
                .build();
    }

}