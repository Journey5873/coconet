package com.coconet.memberservice.security.oauth.member;

import java.util.Map;

public class GithubOauth2MemberInfo extends Oauth2MemberInfo{
    public GithubOauth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return "github";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
