package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    TokenResponse login(String email);

    TokenResponse register(MemberRegisterRequestDto requestDto);

    MemberResponseDto getUserInfo(String memberId);

    MemberResponseDto updateUserInfo(String userId, MemberRequestDto requestDto, MultipartFile imageFile);

    MemberResponseDto deleteUser(String userId);
}
