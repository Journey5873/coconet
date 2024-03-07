package com.coconet.chatservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MemberDto {
    private String name;
    private byte[] image;
}
