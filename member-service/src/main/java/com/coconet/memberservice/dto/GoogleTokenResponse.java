package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class GoogleTokenResponse {
    private String access_token;
    private String expires_in;
    private String cope;
    private String token_type;
    private String id_token;
}
