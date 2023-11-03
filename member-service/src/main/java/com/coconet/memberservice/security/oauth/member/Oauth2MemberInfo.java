package com.coconet.memberservice.security.oauth.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class Oauth2MemberInfo {

    protected Map<String, Object> attributes;

    //소셜 로그인한 provider의 sub에 해당합니다.
    public abstract String getId();

    //소셜 로그인한 provider의 name에 해당합니다.
    public abstract String getName();

    public abstract String getEmail();
}

