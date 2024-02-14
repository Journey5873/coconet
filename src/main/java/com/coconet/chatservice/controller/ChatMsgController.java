package com.coconet.chatservice.controller;

import com.coconet.chatservice.dto.ChatMsgCreateRequestDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.service.ChatMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatMsgController {

    private final ChatMsgService chatMsgService;
    private final SimpMessageSendingOperations sendingOperations;
    @Value("${chatroom.URI}")
    private String roomURI;

    @MessageMapping("/message")
    public ChatMsgResponseDto sendChat(ChatMsgCreateRequestDto requestDto) {

        ChatMsgResponseDto response = chatMsgService.sendChat(requestDto);
        sendingOperations.convertAndSend(roomURI + requestDto.getRoomUUID()
                , response);

        return response;
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "waeeeeee";
    }

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        log.info("connect");
    }
}