package com.coconet.memberservice.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {
    private String access_token;
    private String token_type;
    private String scope;

}
