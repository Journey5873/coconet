package com.coconet.memberservice.security.oauth.member;

import java.util.Map;

public class GoogleOauth2MemberInfo extends Oauth2MemberInfo{

    public GoogleOauth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImage() {
        return (String) attributes.get("picture");
    }
}
