package com.coconet.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatMsgCreateRequestDto {
    private UUID senderUUID;
    private UUID receiverUUID;
    private UUID roomUUID;
    private String message;
    private LocalDateTime sentAt;
}
