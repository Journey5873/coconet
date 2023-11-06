package com.coconet.memberservice.controller;

import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.security.auth.MemberPrincipal;
import com.coconet.memberservice.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/api")
public class PrivateMemberController {

    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/my-profile")
    public Response<MemberResponseDto> getUserInfo(@RequestHeader(value="memberId") String memberId) {
        MemberResponseDto memberResponseDto = memberServiceImpl.getUserInfo(memberId);
        return Response.OK(memberResponseDto);
    }

    @PutMapping("/my-profile")
    public Response<MemberResponseDto> updateUserInfo(@RequestPart("requestDto") MemberRequestDto requestDto, @RequestPart("imageFile") MultipartFile imageFile, @RequestHeader(value="memberId") String memberId) {
        MemberResponseDto memberResponseDto = memberServiceImpl.updateUserInfo(memberId, requestDto, imageFile);
        return Response.OK(memberResponseDto);
    }

    @DeleteMapping("/delete")
    public Response<MemberResponseDto> deleteUser(@RequestHeader(value="memberId") String memberId) {
        MemberResponseDto memberResponseDto = memberServiceImpl.deleteUser(memberId);
        return Response.OK(memberResponseDto);
    }

    @GetMapping("/health")
    public String health() {
        return "hello";
    }

}