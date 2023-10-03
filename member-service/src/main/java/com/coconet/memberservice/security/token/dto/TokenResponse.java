package com.coconet.memberservice.security.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;
}
