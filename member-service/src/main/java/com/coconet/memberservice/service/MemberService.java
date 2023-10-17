package com.coconet.memberservice.service;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.*;
import com.coconet.memberservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final TechStackRepository techStackRepository;
    private final MemberStackRepository memberStackRepository;

    public MemberResponseDto getUserInfo(Long id){
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        List<String> returnRoles = getAllRoles(member).stream()
                .map(RoleEntity::getName)
                .toList();
        List<String> returnStacks = getAllStacks(member).stream()
                .map(TechStackEntity::getName)
                .toList();

        return MemberResponseDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(member.getProfileImage())
                .roles(returnRoles)
                .stacks(returnStacks)
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .build();
    }

    public MemberResponseDto updateUserInfo(Long id, MemberRequestDto requestDto, MultipartFile imageFile) {
        MemberEntity member = memberRepository.findById(id)
                                                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        member.changeName(requestDto.getName());
        member.changeCareer(String.valueOf(requestDto.getCareer()));
        member.changeProfileImage(updateProfilePic(member, imageFile));
        member.changeGithubLink(requestDto.getGithubLink());
        member.changeBlogLink(requestDto.getBlogLink());
        member.changeNotionLink(requestDto.getNotionLink());

        MemberEntity returnMember = memberRepository.save(member);

        List<String> returnRoles = updateRoles(member, requestDto.getRoles());
        List<String> returnStacks = updateStacks(member, requestDto.getStacks());

        return MemberResponseDto.builder()
                .name(returnMember.getName())
                .career(Integer.parseInt(returnMember.getCareer()))
                .profileImg(returnMember.getProfileImage())
                .roles(returnRoles)
                .stacks(returnStacks)
                .githubLink(returnMember.getGithubLink())
                .blogLink(returnMember.getBlogLink())
                .notionLink(returnMember.getNotionLink())
                .build();
    }

    public MemberResponseDto deleteUser(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        member.deleteUser();

        MemberEntity returnMember = memberRepository.save(member);

        List<String> returnRoles = getAllRoles(member).stream()
                .map(RoleEntity::getName)
                .toList();
        List<String> returnStacks = getAllStacks(member).stream()
                .map(TechStackEntity::getName)
                .toList();

        return MemberResponseDto.builder()
                .name(returnMember.getName())
                .career(Integer.parseInt(returnMember.getCareer()))
                .profileImg(returnMember.getProfileImage())
                .roles(returnRoles)
                .stacks(returnStacks)
                .githubLink(returnMember.getGithubLink())
                .blogLink(returnMember.getBlogLink())
                .notionLink(returnMember.getNotionLink())
                .build();
    }

    public List<RoleEntity> getAllRoles(MemberEntity member) {
        List<MemberRoleEntity> roleEntities = memberRoleRepository.findAllByMemberId(member.getId());

        if(roleEntities.size() == 0) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        return roleEntities.stream()
                .map(MemberRoleEntity::getRole)
                .toList();
    }

    public List<TechStackEntity> getAllStacks(MemberEntity member){
        List<MemberStackEntity> stackEntities = memberStackRepository.findByMemberId(member.getId());

        if(stackEntities.size() == 0) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        return stackEntities.stream()
                .map(MemberStackEntity::getTechStack)
                .toList();
    }

    public String updateProfilePic(MemberEntity member, MultipartFile image) {

        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "member-service/src/main/resources/memberProfilePics";

        if (!image.isEmpty()) {
            imagePath = path + "/" + member.getId() + ".png";
            File file = new File(absolutePath + imagePath);

            try {
                image.transferTo(file);
            } catch (IOException e) {
                throw new ApiException(ErrorCode.SERVER_ERROR);
            }

            member.changeProfileImage(imagePath);
            memberRepository.save(member);
        } else {
            File previousFile = new File(absolutePath + path + "/" + member.getId() + ".png");
            if(previousFile.exists())
                previousFile.delete();
            return path + "/basic_image.png";
        }
        return imagePath;
    }

    public List<String> updateRoles(MemberEntity member, List<String> roles) {

        List<RoleEntity> inputRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND))
                )
                .toList();

        // Get current roles
        List<RoleEntity> currentRoles = getAllRoles(member);

        // Identify new roles to add
        List<RoleEntity> rolesToAdd = inputRoles.stream()
                .filter(role -> !currentRoles.contains(role))
                .toList();

        // Identify roles to remove
        List<RoleEntity> rolesToRemove = currentRoles.stream()
                .filter(role -> !inputRoles.contains(role))
                .toList();

        // Create MemberRoleEntity to add
        List<MemberRoleEntity> memberRoleEntities = rolesToAdd.stream()
                .map(role -> new MemberRoleEntity(member, role))
                .toList();

        // save
        memberRoleRepository.saveAll(memberRoleEntities);

        // remove
        memberRoleRepository.deleteByMemberIdAndRoleIdIn(member.getId(), rolesToRemove.stream().map(role -> role.getId()).toList());

        return memberRoleRepository.findByMemberId(member.getId()).stream()
                .map(memberRoleEntity -> memberRoleEntity.getRole().getName())
                .toList();
    }

    List<String> updateStacks(MemberEntity member, List<String> stacks){
        List<TechStackEntity> inputStacks = stacks.stream()
                .map(stackName -> techStackRepository.findByName(stackName)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND))
                )
                .toList();

        // Get current stacks
        List<TechStackEntity> currentStacks = getAllStacks(member);

        // Identify new stacks to add
        List<TechStackEntity> stacksToAdd = inputStacks.stream()
                .filter(stack -> !currentStacks.contains(stack))
                .toList();

        // Identify stacks to remove
        List<TechStackEntity> stacksToRemove = currentStacks.stream()
                .filter(stack -> !inputStacks.contains(stack))
                .toList();

        // Create MemberStackEntity to add
        List<MemberStackEntity> memberStackEntities = stacksToAdd.stream()
                .map(stack -> new MemberStackEntity(member, stack))
                .toList();

        // Save new stacks
        memberStackRepository.saveAll(memberStackEntities);

        // Delete stacks
        memberStackRepository.deleteByMemberIdAndTechStackIdIn(member.getId(), stacksToRemove.stream().map(stack -> stack.getId()).toList());

        return memberStackRepository.findByMemberId(member.getId()).stream()
                .map(memberStackEntity -> memberStackEntity.getTechStack().getName())
                .toList();
    }
}

