package com.coconet.chatservice.converter;

import com.coconet.chatservice.client.MemberClient;
import com.coconet.chatservice.client.MemberDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.dto.client.MemberResponse;
import com.coconet.chatservice.entity.ChatMsgEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Component
public class ChatMsgEntityToConverter {
    private static MemberClient memberClient;

    @Autowired
    public ChatMsgEntityToConverter(MemberClient memberClient) {
        this.memberClient = memberClient;
    }

    public static ChatMsgResponseDto convertToDto(ChatMsgEntity chatMsgEntity, UUID senderUUID) {
        MemberResponse memberResponse = memberClient.sendChatClient(senderUUID).getData();
        MemberDto memberDto = MemberDto.builder()
                .name(memberResponse.getName())
                .image(memberResponse.getProfileImage())
                .build();

        return ChatMsgResponseDto.builder()
                .chatMsgUUID(chatMsgEntity.getChatUUID())
                .sender(memberDto)
                .message(chatMsgEntity.getMessage())
                .build();
    }

}