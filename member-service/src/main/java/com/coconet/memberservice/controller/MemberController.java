package com.coconet.memberservice.controller;

import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.*;
import com.coconet.memberservice.security.auth.MemberPrincipal;
import com.coconet.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Member")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/open-api")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/my-profile")
    public Response<MemberResponseDto> getUserInfo(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long id = memberPrincipal.getMember().getId();
        MemberResponseDto memberResponseDto = memberService.getUserInfo(id);
        return Response.OK(memberResponseDto);
    }

    @PutMapping("/my-profile")
    public Response<MemberResponseDto> updateUserInfo(@RequestPart("requestDto") MemberRequestDto requestDto, @RequestPart("imageFile") MultipartFile imageFile, @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long id = memberPrincipal.getMember().getId();
        MemberResponseDto memberResponseDto = memberService.updateUserInfo(id, requestDto, imageFile);
        return Response.OK(memberResponseDto);
    }

    @DeleteMapping("/delete")
    public Response<MemberResponseDto> deleteUser(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long id = memberPrincipal.getMember().getId();
        MemberResponseDto memberResponseDto = memberService.deleteUser(id);
        return Response.OK(memberResponseDto);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Hello world";
    }

}