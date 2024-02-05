package com.coconet.articleservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatClientResponseDto {
    private UUID articleUUID;
    private String roomName;
    private UUID writerUUID;
}
