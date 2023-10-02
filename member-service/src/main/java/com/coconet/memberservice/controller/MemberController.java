package com.coconet.memberservice.controller;

import com.coconet.memberservice.entity.MemberRoleEntity;
import com.coconet.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/member-service/")
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        memberService.deleteUser(id);
        return "successfully deleted";
    }

    @GetMapping("/role/{id}")
    public Optional<MemberRoleEntity> getUserAndRole(@PathVariable Long id) {
        return memberService.viewAllRoles(id, 1L);
    }
}
