package com.coconet.memberservice.controller;

import com.coconet.memberservice.dto.*;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberRoleEntity;
import com.coconet.memberservice.entity.RoleEntity;
import com.coconet.memberservice.entity.MemberStackEntity;
import com.coconet.memberservice.entity.TechStackEntity;
import com.coconet.memberservice.service.MemberService;
import com.coconet.memberservice.service.MemberStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.coconet.common.response.*;

import java.util.List;
import java.util.Optional;

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
    public String deleteUser(Long id) {
        memberService.deleteUser(id);
        return "successfully deleted";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Hello world";
    }

}