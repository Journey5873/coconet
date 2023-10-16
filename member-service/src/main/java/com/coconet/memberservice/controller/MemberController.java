package com.coconet.memberservice.controller;

import com.coconet.memberservice.dto.*;
import com.coconet.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/open-api")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/my-profile")
    public MemberResponseDto getUserInfo(){
        long id = 2L;

        return memberService.getUserInfo(id);
    }


    @PutMapping("/my-profile")
    public MemberResponseDto updateUserInfo(@RequestPart MemberRequestDto requestDto, @RequestPart MultipartFile imageFile) {
        long id = 2L;

        return memberService.updateUserInfo(id, requestDto, imageFile);
    }

    @DeleteMapping("/delete")
    public MemberResponseDto deleteUser() {
        long id = 2L;

        return memberService.deleteUser(id);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Hello world";
    }

}