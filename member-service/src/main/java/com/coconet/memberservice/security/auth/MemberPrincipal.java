package com.coconet.memberservice.security.auth;

import com.coconet.memberservice.entity.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MemberPrincipal implements UserDetails, OAuth2User{

    private MemberEntity member;
    private Map<String, Object> attribute;

    public MemberPrincipal(MemberEntity member) {
        this.member = member;
    }

    public static MemberPrincipal create(MemberEntity member, Map<String, Object> attribute) {
        MemberPrincipal memberPrincipal = new MemberPrincipal(member);
        memberPrincipal.setAttribute(attribute);
        return memberPrincipal;
    }

    @Override
    public String getName() {
        return member.getMemberId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
