package com.coconet.chatservice.service;

import com.coconet.chatservice.converter.ChatMsgEntityToConverter;
import com.coconet.chatservice.dto.ChatMsgCreateRequestDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.entity.ChatMsgEntity;
import com.coconet.chatservice.mongo.ChatMsgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMsgService {
    private final ChatMsgRepository chatMsgRepository;

    public ChatMsgResponseDto sendChat(ChatMsgCreateRequestDto requestDto){
        ChatMsgEntity chatMsgEntity = ChatMsgEntity.builder()
                .senderUUID(requestDto.getSenderUUID().toString())
                .roomUUID(requestDto.getRoomUUID().toString())
                .message(requestDto.getMessage())
                .build();
        ChatMsgEntity savedMsgEntity = chatMsgRepository.save(chatMsgEntity);
        return ChatMsgEntityToConverter.convertToDto(savedMsgEntity);
    }

    public List<ChatMsgResponseDto> loadChats(String roomUUID){
        List<ChatMsgEntity> chats = chatMsgRepository.findAllByRoomUUID(roomUUID);

        return chats.stream()
                .map(chatMsgEntity -> ChatMsgEntityToConverter.convertToDto(chatMsgEntity))
                .toList();
    }
}
