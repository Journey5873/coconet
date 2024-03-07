package com.coconet.chatservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class ArticleDto {
    private String name;
    private UUID articleUUID;
}
