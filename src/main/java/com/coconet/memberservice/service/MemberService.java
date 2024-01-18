package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.security.oauthModel.AuthProvider;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MemberService {
    TokenResponse login(String email);

    UUID preRegister(AuthProvider provider, String email);

    TokenResponse register(MemberRegisterRequestDto requestDto, MultipartFile imageFile);

    String updateProfilePic(MemberEntity member, MultipartFile image);

    MemberResponseDto getUserInfo(UUID memberId);

    MemberResponseDto updateUserInfo(UUID memberId, MemberRequestDto requestDto, MultipartFile imageFile);

    MemberResponseDto deleteUser(UUID memberId);

    Boolean checkMemberNickName(String nickName);
}