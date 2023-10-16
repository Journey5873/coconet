package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.*;
import com.coconet.memberservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new IllegalArgumentException("No member found"));

        List<String> returnRoles = getAllRoles(id);
        List<String> returnStacks = getAllStacks(id);
        return MemberResponseDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(member.getProfileImage())
                .roles(returnRoles)
                .stacks(returnStacks)
                .build();
    }

    public MemberResponseDto updateUserInfo(Long id, MemberRequestDto requestDto, MultipartFile imageFile) {

        MemberEntity member = memberRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException("No member found"));

        try {
            member.changeName(requestDto.getName());
            member.changeCareer(String.valueOf(requestDto.getCareer()));
            member.changeProfileImage(updateProfilePic(id, imageFile));
            member.changeGithubLink(requestDto.getGithubLink());
            member.changeBlogLink(requestDto.getBlogLink());
            member.changeNotionLink(requestDto.getNotionLink());

            MemberEntity returnMember = memberRepository.save(member);

            List<String> returnRoles = updateRoles(id, requestDto.getRoles());
            List<String> returnStacks = updateStacks(id, requestDto.getStacks());

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
        catch(Exception e) {
            System.out.println(e);
            new IllegalArgumentException("Update fails");
            return null;
        }
    }

    public MemberResponseDto deleteUser(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No member found"));

        member.deleteUser();

        MemberEntity returnMember = memberRepository.save(member);

        List<String> returnRoles = getAllRoles(id);
        List<String> returnStacks = getAllStacks(id);

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

    public List<String> getAllRoles(Long memberId) {
        List<MemberRoleEntity> ids = memberRoleRepository.findAllByMemberId(memberId);
        if(ids.size() == 0) {
            throw new IllegalArgumentException("");
        }

        List<String> returnRoles = new ArrayList<>();
        for (MemberRoleEntity memberRoleEntity : ids) {
            returnRoles.add(memberRoleEntity.getRole().getName());
        }
        return returnRoles;
    }

    public List<String> getAllStacks(Long memberId){
        List<MemberStackEntity> ids = memberStackRepository.findByMemberId(memberId);
        if(ids.size() == 0) {
            throw new IllegalArgumentException("");
        }

        List<String> returnStacks = new ArrayList<>();
        for (MemberStackEntity memberStack : ids) {
            returnStacks.add(memberStack.getTechStack().getName());
        }
        return returnStacks;
    }

    public String updateProfilePic(Long id, MultipartFile image) throws Exception {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No member found"));

        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "member-service/src/main/resources/memberProfilePics";

        if (!image.isEmpty()) {
            imagePath = path + "/" + id + ".png";
            File file = new File(absolutePath + imagePath);
            image.transferTo(file);

            member.changeProfileImage(imagePath);
            memberRepository.save(member);
        } else {
            File previousFile = new File(absolutePath + path + "/" + id + ".png");
            if(previousFile.exists())
                previousFile.delete();
            return path + "/basic_image.png";
        }

        return imagePath;
    }

    public List<String> updateRoles(Long memberId, List<String> roles) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Long> inputRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName))
                )
                .map(RoleEntity::getId)
                .toList();

        // Get current roles
        List<Long> currentRoles = memberRoleRepository.findByMemberId(memberId)
                .stream()
                .map(MemberRoleEntity -> MemberRoleEntity.getRole().getId())
                .toList();

        // Identify new roles to add
        List<Long> rolesToAdd = inputRoles.stream()
                .filter(roleId -> !currentRoles.contains(roleId))
                .toList();

        // Identify roles to remove
        List<Long> rolesToRemove = currentRoles.stream()
                .filter(currRoleId -> !inputRoles.contains(currRoleId))
                .toList();

        // Create MemberRoleEntity to add
        List<MemberRoleEntity> memberRoleEntitiesToAdd = rolesToAdd.stream()
                .map(roleId -> MemberRoleEntity.builder()
                        .member(member)
                        .role(roleRepository.findById(roleId)
                                .orElseThrow(() -> new IllegalArgumentException("No role exists"))
                        )
                        .build()
                )
                .collect(Collectors.toList());
        memberRoleRepository.saveAll(memberRoleEntitiesToAdd);

        // Get MemberRoleEntity to remove
        List<MemberRoleEntity> memberRoleEntitiesToRemove = rolesToRemove.stream()
                .map(roleId -> memberRoleRepository.findByMemberIdAndRoleId(memberId, roleId)
                        .orElseThrow(() -> new IllegalArgumentException("No Role"))
                )
                .collect(Collectors.toList());
        memberRoleRepository.deleteAllInBatch(memberRoleEntitiesToRemove);

        return memberRoleRepository.findByMemberId(memberId).stream()
                .map(memberRoleEntity -> memberRoleEntity.getRole().getName())
                .toList();
    }

    List<String> updateStacks(Long memberId, List<String> stacks){
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Long> stacksId = stacks.stream()
                .map(stackName -> techStackRepository.findByName(stackName)
                        .orElseThrow(() -> new IllegalArgumentException("Stack not found: " + stackName))
                )
                .map(TechStackEntity::getId)
                .toList();

        // Get current stacks
        List<Long> currentStacks = memberStackRepository.findByMemberId(memberId)
                .stream()
                .map(MemberStackEntity -> MemberStackEntity.getTechStack().getId())
                .toList();

        // Identify new stacks to add
        List<Long> stacksToAdd = stacksId.stream()
                .filter(stackId -> !currentStacks.contains(stackId))
                .toList();

        // Identify stacks to remove
        List<Long> stacksToRemove = currentStacks.stream()
                .filter(currStackId -> !stacksId.contains(currStackId))
                .toList();

        // Create MemberStackEntity to add
        List<MemberStackEntity> memberStackEntitiesToAdd = stacksToAdd.stream()
                .map(stackId -> MemberStackEntity.builder()
                        .member(member)
                        .techStack(techStackRepository.findById(stackId)
                                .orElseThrow(() -> new IllegalArgumentException("No stack exists"))
                        )
                        .build()
                )
                .collect(Collectors.toList());
        // Save new stacks
        memberStackRepository.saveAll(memberStackEntitiesToAdd);

        // Get MemberStackEntity to remove
        List<MemberStackEntity> memberStackEntitiesToRemove = stacksToRemove.stream()
                .map(stackId -> memberStackRepository.findByMemberIdAndTechStackId(memberId, stackId)
                        .orElseThrow(() -> new IllegalArgumentException("No stack"))
                )
                .collect(Collectors.toList());
        // Delete stacks
        memberStackRepository.deleteAllInBatch(memberStackEntitiesToRemove);

        return memberStackRepository.findByMemberId(memberId).stream()
                .map(memberStackEntity -> memberStackEntity.getTechStack().getName())
                .toList();
    }
}

