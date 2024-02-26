package com.coconet.chatservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class MemberResponse {
    private String email;
    private String name;
    private UUID memberUUID;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

