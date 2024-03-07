package com.coconet.chatservice.handler;

import com.coconet.chatservice.repository.ChatRoomRepository;
import com.coconet.chatservice.service.ChatRoomSubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketChatHandler implements ChannelInterceptor {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {

        } else if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            validateSubscriptionHeader(headerAccessor);
        }
        return message;
    }

    private void validateSubscriptionHeader(StompHeaderAccessor headerAccessor) {
        UUID memberUUID = (UUID) headerAccessor.getSessionAttributes().get("memberUUID");
        String destination = headerAccessor.getDestination();
        UUID roomUUID = UUID.fromString(destination.split("/")[3]);

        if (!chatRoomRepository.isMember(memberUUID, roomUUID)) {
            throw new IllegalStateException("You cannot join the room");
        }
    }
}
