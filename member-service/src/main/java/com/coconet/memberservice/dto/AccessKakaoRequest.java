package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccessKakaoRequest {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code;
}
