package com.coconet.chatservice.controller;

import com.coconet.chatservice.dto.ChatMsgCreateRequestDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.service.ChatMsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-service")
public class ChatMsgController {

    private final ChatMsgService chatMsgService;
    private final SimpMessageSendingOperations sendingOperations;
    @Value("${chatroom.URI}")
    private String roomURI;

    // 안되면 config에 주석 푸세염 ^^
    @MessageMapping("/app/message")
    public ChatMsgResponseDto sendChat(ChatMsgCreateRequestDto requestDto) {

        ChatMsgResponseDto response = chatMsgService.sendChat(requestDto);
        sendingOperations.convertAndSend(roomURI + requestDto.getRoomUUID()
                , response);

        return response;
    }
}

