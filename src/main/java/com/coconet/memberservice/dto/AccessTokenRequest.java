package com.coconet.memberservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccessTokenRequest {

    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
    private String redirect_uri;
}
