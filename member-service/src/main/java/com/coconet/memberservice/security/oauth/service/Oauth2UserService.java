package com.coconet.memberservice.security.oauth.service;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.repository.MemberRepository;
import com.coconet.memberservice.security.auth.MemberPrincipal;
import com.coconet.memberservice.security.oauth.member.Oauth2MemberInfo;
import com.coconet.memberservice.security.oauth.member.Oauth2MemberInfoFactory;
import com.coconet.memberservice.security.oauth.model.AuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        AuthProvider authProvider = AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId());
        Oauth2MemberInfo oauth2MemberInfo = Oauth2MemberInfoFactory
            .getOAuth2UserInfo(
                    userRequest.getClientRegistration().getRegistrationId(),
                    oAuth2User.getAttributes()
            );

        if (!StringUtils.hasText(oauth2MemberInfo.getEmail())) {
            throw new RuntimeException("Email not found from Oauth2 provider");
        }

        MemberEntity member = memberRepository.findByEmail(oauth2MemberInfo.getEmail()).orElse(null);

        //register
        if (member == null) {
            member = registerUser(authProvider, oauth2MemberInfo);
        }

        return MemberPrincipal.create(member, oauth2MemberInfo.getAttributes());
    }

    private MemberEntity registerUser(AuthProvider authProvider, Oauth2MemberInfo oAuth2UserInfo) {
        MemberEntity user = MemberEntity.builder()
                .email(oAuth2UserInfo.getEmail())
                .name("Undefined")
                .career("Undefined")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .provider(authProvider)
                .isActivated((byte) 1)
                .memberId(UUID.randomUUID().toString())
                .build();
        return memberRepository.save(user);
    }

}
