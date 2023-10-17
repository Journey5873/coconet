package com.coconet.memberservice.controller;

import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.*;
import com.coconet.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/open-api")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/my-profile/{id}")
    public Response<MemberResponseDto> getUserInfo(@PathVariable("id") Long id){
        //Testing 용이라 무시하셔도 됩니다..
    //        long id = 2L;
        MemberResponseDto memberResponseDto = memberService.getUserInfo(id);
        return Response.OK(memberResponseDto);
    }

    @PutMapping("/my-profile")
    public Response<MemberResponseDto> updateUserInfo(@RequestPart("requestDto") MemberRequestDto requestDto, @RequestPart("imageFile") MultipartFile imageFile) {
        long id = 2L;
        MemberResponseDto memberResponseDto = memberService.updateUserInfo(id, requestDto, imageFile);
        return Response.OK(memberResponseDto);
    }

    @DeleteMapping("/delete")
    public Response<MemberResponseDto> deleteUser() {
        long id = 2L;
        MemberResponseDto memberResponseDto = memberService.deleteUser(id);
        return Response.OK(memberResponseDto);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Hello world";
    }

}