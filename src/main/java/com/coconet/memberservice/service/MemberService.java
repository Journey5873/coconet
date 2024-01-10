package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MemberService {
    TokenResponse login(String email);

    TokenResponse register(MemberRegisterRequestDto requestDto, MultipartFile imageFile);

    MemberResponseDto getUserInfo(UUID memberId);

    MemberResponseDto updateUserInfo(UUID memberId, MemberRequestDto requestDto, MultipartFile imageFile);

    MemberResponseDto deleteUser(UUID memberId);
}