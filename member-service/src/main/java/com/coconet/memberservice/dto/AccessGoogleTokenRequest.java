package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@Getter
public class AccessGoogleTokenRequest {

    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
    private String redirect_uri;
}
