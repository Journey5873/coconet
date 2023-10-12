package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.*;
import com.coconet.memberservice.repository.*;
import lombok.RequiredArgsConstructor;
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

            MemberEntity returnMember = memberRepository.save(member);

            List<String> returnRoles = updateRoles(id, requestDto.getRoles());
            List<String> returnStacks = updateStacks(id, requestDto.getStacks());
            return MemberResponseDto.builder()
                    .name(returnMember.getName())
                    .career(Integer.parseInt(returnMember.getCareer()))
                    .profileImg(returnMember.getProfileImage())
                    .roles(returnRoles)
                    .stacks(returnStacks)
                    .build();
        }
        catch(Exception e) {
            new IllegalArgumentException("Update fails");
            return null;
        }
    }

    public void deleteUser(Long id) {
        memberRepository.deleteById(id);
        // 제거 => DB 제거가 아니라 값만 활성화 인지 아닌지 .
        // User status 0 (deleted)
        // login X ..
        // 활성화된 애들만 => 비활성화된 애들은 새로운 유저로.
        //
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
        Optional<MemberEntity> member = memberRepository.findById(id);
        if (!member.isPresent()) {
            System.out.println("error");
            return "Error";
        }

        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "member-service/src/main/resources/memberProfilePics";

        File file = new File(path);
        if (!image.isEmpty()) {
            String contentType = image.getContentType();
            String originalFileExtension;
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
            } else {
                if (contentType.contains("image/jpeg") || contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                }
            }
            imagePath = path + "/" + id + originalFileExtension;
            file = new File(absolutePath + imagePath);
            image.transferTo(file);

            MemberEntity memberEntity = member.get();
            memberEntity.changeProfileImage(imagePath);
            memberRepository.save(memberEntity);
        } else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }

        return imagePath;
    }

    public List<String> updateRoles(Long id, List<String> roles) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Long> rolesId = new ArrayList<>();
        for(String role :roles) {
            RoleEntity roleEntity = roleRepository.findByName(role)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            rolesId.add(roleEntity.getId());
        }
        List<MemberRoleEntity> memberRoleEntities = memberRoleRepository.findByMemberId(id);
        List<Long> currentRoles = new ArrayList<>();
        List<Long> duplicatedIds;

        for (MemberRoleEntity memberRoleEntity : memberRoleEntities) {
            currentRoles.add(memberRoleEntity.getRole().getId());
        }
        duplicatedIds = new ArrayList<>(currentRoles);

        duplicatedIds.retainAll(roles);
        currentRoles.removeAll(duplicatedIds);
        roles.removeAll(duplicatedIds);

        // Add roles
        List<MemberRoleEntity> memberRoleEntityList = new ArrayList<>();
        for (int i = 0; i < rolesId.size(); i++) {
            RoleEntity role = roleRepository.findById(rolesId.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("No Role exists"));
            memberRoleEntityList.add(MemberRoleEntity.builder()
                    .member(member)
                    .role(role)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
        memberRoleRepository.saveAll(memberRoleEntityList);

        //Remove roles
        List<MemberRoleEntity> removeArr = new ArrayList<>();
        for (int i = 0; i < currentRoles.size(); i++) {
            RoleEntity role = roleRepository.findById(currentRoles.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("no role"));
                removeArr.add(memberRoleRepository.findByMemberIdAndRoleId(id, currentRoles.get(i)).get());
            }
        memberRoleRepository.deleteAllInBatch(removeArr);

        //retrieve roles from db
        List<MemberRoleEntity> ids = memberRoleRepository.findByMemberId(id);
        List<String> returnRoles = new ArrayList<>();
        for(MemberRoleEntity memberRole : ids) {
            returnRoles.add(memberRole.getRole().getName());
        }
        return returnRoles;
    }

    List<String> updateStacks(Long id, List<String> stacks){
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Long> stacksId = stacks.stream()
                .map(stackName -> techStackRepository.findByName(stackName)
                        .orElseThrow(() -> new IllegalArgumentException("Stack not found: " + stackName))
                )
                .map(TechStackEntity::getId)
                .collect(Collectors.toList());

        // Get current stacks
        List<Long> currentStacks = memberStackRepository.findByMemberId(id)
                .stream()
                .map(MemberStackEntity -> MemberStackEntity.getTechStack().getId())
                .collect(Collectors.toList());

        // Identify new stacks to add
        List<Long> stacksToAdd = stacksId.stream()
                .filter(stackId -> !currentStacks.contains(stackId))
                .collect(Collectors.toList());

        // Identify stacks to remove
        List<Long> stacksToRemove  = currentStacks.stream()
                .filter(stackId -> !stacksId.contains(stackId))
                .collect(Collectors.toList());

        // Get MemberStackEntity to add
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

        // get MemberStackEntity to remove
        List<MemberStackEntity> memberStackEntitiesToRemove = stacksToRemove.stream()
                .map(stackId -> memberStackRepository.findByMemberIdAndTechStackId(id, stackId)
                        .orElseThrow(() -> new IllegalArgumentException("No stack"))
                )
                .collect(Collectors.toList());
        // Delete stacks
        memberStackRepository.deleteAllInBatch(memberStackEntitiesToRemove);

        List<String> updatedStacks = memberStackRepository.findByMemberId(id).stream()
                .map(memberStackEntity -> memberStackEntity.getTechStack().getName())
                .collect(Collectors.toList());

        return updatedStacks;
    }
}

