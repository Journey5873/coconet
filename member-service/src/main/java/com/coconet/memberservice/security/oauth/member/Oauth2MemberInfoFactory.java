package com.coconet.memberservice.security.oauth.member;

import com.coconet.memberservice.security.oauth.model.AuthProvider;

import java.util.Map;

public class Oauth2MemberInfoFactory {

    public static Oauth2MemberInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOauth2MemberInfo(attributes);
        }
        else {
            throw new RuntimeException("존재하지 않는 프로바이더");
        }
    }
}
