package com.coconet.memberservice.controller;

import com.coconet.memberservice.entity.MemberRoleEntity;
import com.coconet.memberservice.entity.RoleEntity;
import com.coconet.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/member-service/")
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping("/delete/")
    public String deleteUser(Long id) {
        memberService.deleteUser(id);
        return "successfully deleted";
    }

    @GetMapping("/role/")
    public List<RoleEntity> getUserAndRole(Long id) {
        return memberService.viewAllRoles(id);
    }

    @PostMapping("/profilePic/")
    public String updateProfile(Long id, MultipartFile image) throws Exception {
        return memberService.updateProfilePic(id, image);
    }

    @PostMapping("/role/")
    public List<Long> updateRoles(Long id, @RequestBody List<Long> rolesIds) {
        return memberService.updateRoles(id, rolesIds);
    }


}