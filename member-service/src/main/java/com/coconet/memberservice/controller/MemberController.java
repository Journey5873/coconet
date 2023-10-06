package com.coconet.memberservice.controller;

import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberStackResponseDto;
import com.coconet.memberservice.dto.UpdateRequestDto;
import com.coconet.memberservice.dto.UpdateStackRequestDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberRoleEntity;
<<<<<<< HEAD
import com.coconet.memberservice.entity.RoleEntity;
=======
import com.coconet.memberservice.entity.MemberStackEntity;
import com.coconet.memberservice.entity.TechStackEntity;
>>>>>>> ca693336ea3548b825ba83818cc6d5f31fb6004b
import com.coconet.memberservice.service.MemberService;
import com.coconet.memberservice.service.MemberStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;
=======
import com.coconet.common.response.*;
>>>>>>> ca693336ea3548b825ba83818cc6d5f31fb6004b

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/member-service/")
public class MemberController {

    private final MemberService memberService;
    private final MemberStackService memberStackService;

    @DeleteMapping("/delete/")
    public String deleteUser(Long id) {
        memberService.deleteUser(id);
        return "successfully deleted";
    }

<<<<<<< HEAD
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


=======
    @GetMapping("/role/{id}")
    public Optional<MemberRoleEntity> getUserAndRole(@PathVariable Long id) {
        return memberService.viewAllRoles(id, 1L);
        return null;
    }

    @PutMapping("/update/username")
    public String updateMemberUsername(@RequestBody UpdateRequestDto request){
        Optional<UpdateRequestDto> updatedMember = memberService.updateName(request);
        if (updatedMember.isPresent()) {
            return "Successfully updated";
        } else {
            return "Member not found";
        }
    }

    @PutMapping("/update/career")
    public String updateMemberCareer(@RequestBody UpdateRequestDto request){
        Optional<UpdateRequestDto> updatedMember = memberService.updateCareer(request);
        if (updatedMember.isPresent()) {
            return "Successfully updated";
        } else {
            return "Member not found";
        }
    }

    @GetMapping("/member-stacks")
    public List<MemberStackResponseDto> getStacks(@RequestBody MemberRequestDto request){
        return memberStackService.getAllStacks(request.getId());
    }

    @PutMapping("/update/member-stack")
    public String updateMemberStacks(@RequestBody UpdateStackRequestDto request) {
        try {
            memberStackService.updateMemberStacks(request.getMemberId(), request.getStackIds());
            return "Successfully updated";
        } catch (IllegalArgumentException e) {
            return "Member not found";
        } catch (Exception e) {
            return "Failed to update member stacks";
        }
    }
>>>>>>> ca693336ea3548b825ba83818cc6d5f31fb6004b
}