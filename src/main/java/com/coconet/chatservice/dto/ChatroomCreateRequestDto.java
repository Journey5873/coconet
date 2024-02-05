package com.coconet.chatservice.dto;

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
public class ChatroomCreateRequestDto {
    private UUID articleUUID;
}

// post : 자바 개발자 구합니다
// 글쓴이 : 캥거루

// 글쓴이 시점
// 자바 개발자 구합니다 w 쿼카
// 자바 개발자 구합니다 w 나현

// 쿼카 시점
// 자바 개발자 구합니다 w 캥거루