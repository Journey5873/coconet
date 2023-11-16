package com.coconet.memberservice.security.token.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;
    private UUID memberId;
}
