package com.coconet.chatservice.controller;

import com.coconet.chatservice.dto.ChatMsgCreateRequestDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.service.ChatMsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMsgController {

    private final ChatMsgService chatMsgService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/message")
    public ChatMsgResponseDto sendChat(ChatMsgCreateRequestDto requestDto) {

        ChatMsgResponseDto chatMsgResponseDto = chatMsgService.sendChat(requestDto);
        sendingOperations.convertAndSend("/queue/room/" + requestDto.getRoomUUID()
                , chatMsgResponseDto);

        return chatMsgResponseDto;
    }
}