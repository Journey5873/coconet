package com.coconet.chatservice.dto;

import com.coconet.chatservice.client.MemberDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMsgResponseDto {
    private UUID chatMsgUUID;
    private UUID senderUUID;
    private MemberDto sender;
//    private UUID roomUUID;
    private String message;
}
