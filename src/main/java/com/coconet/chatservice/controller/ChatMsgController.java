package com.coconet.chatservice.controller;

import com.coconet.chatservice.common.response.Response;
import com.coconet.chatservice.dto.ChatLoadResponseDto;
import com.coconet.chatservice.dto.ChatMsgCreateRequestDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.service.ChatMsgService;
import com.coconet.chatservice.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat-service/api/chat")
public class ChatMsgController {

    private final ChatMsgService chatMsgService;
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatRoomService chatRoomService;
    @Value("${chatroom.URI}")
    private String roomURI;

    @MessageMapping("/message")
    public ChatMsgResponseDto sendChat(ChatMsgCreateRequestDto requestDto) {

        ChatMsgResponseDto response = chatMsgService.sendChat(requestDto);
        sendingOperations.convertAndSend(roomURI + requestDto.getRoomUUID()
                , response);

        return response;
    }

    @MessageMapping("/leave")
    public ChatMsgResponseDto sendLeaveChat(ChatMsgCreateRequestDto requestDto) {
        ChatMsgResponseDto response = chatMsgService.sendLeaveChat(requestDto);
        chatRoomService.leaveRoom(requestDto.getSenderUUID(), requestDto.getRoomUUID());
        sendingOperations.convertAndSend(roomURI + requestDto.getRoomUUID()
                ,response);

        return response;
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "health check";
    }
}