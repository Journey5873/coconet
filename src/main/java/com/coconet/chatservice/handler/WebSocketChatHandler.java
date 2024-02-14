package com.coconet.chatservice.handler;

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

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketChatHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {

            Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
            sessionAttributes.put("memberUUID", "test");
            headerAccessor.setSessionAttributes(sessionAttributes);

        } else if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            validateSubscriptionHeader(headerAccessor);
        }

        return message;
    }

    private void validateSubscriptionHeader(StompHeaderAccessor headerAccessor) {
        String lineName = (String) headerAccessor.getSessionAttributes().get("memberUUID");
        String destination = headerAccessor.getDestination();
        String roomUUID = destination.split("/")[3];

        if (destination != null) {
            throw new IllegalStateException("인증 정보가 잘못되었습니다.");
        }
    }
}
