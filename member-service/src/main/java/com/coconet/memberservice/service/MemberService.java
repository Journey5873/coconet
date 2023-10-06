package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.UpdateRequestDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberRoleEntity;
import com.coconet.memberservice.entity.RoleEntity;
import com.coconet.memberservice.repository.MemberRepository;
import com.coconet.memberservice.repository.MemberRoleRepository;
import com.coconet.memberservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;

    public void deleteUser(Long id) {
        memberRepository.deleteById(id);
        // 제거 => DB 제거가 아니라 값만 활성화 인지 아닌지 .
        // User status 0 (deleted)
        // login X ..
        // 활성화된 애들만 => 비활성화된 애들은 새로운 유저로.
        //
    }

    public List<RoleEntity> viewAllRoles(Long memberId) {
        Optional<MemberEntity> member = memberRepository.findById(memberId);

        if (member.isPresent()) {
            List<MemberRoleEntity> ids = memberRoleRepository.findByMemberId(memberId);
            List<RoleEntity> roleEntities = new ArrayList<>();
            for (MemberRoleEntity memberRoleEntity : ids) {
                roleEntities.add(memberRoleEntity.getRole());
            }
            return roleEntities;
        }
        return null;
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
            memberEntity.setProfileImage(imagePath);
            memberRepository.save(memberEntity);
        } else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }

        return imagePath;
    }

    public List<Long> updateRoles(Long id, List<Long> rolesIds) {
        Optional<MemberEntity> member = memberRepository.findById(id);

        if (member.isPresent()) {
            List<MemberRoleEntity> memberRoleEntities = memberRoleRepository.findByMemberId(id);
            List<Long> currentRoles = new ArrayList<>();
            List<Long> duplicatedIds;

            for (MemberRoleEntity memberRoleEntity : memberRoleEntities) {
                currentRoles.add(memberRoleEntity.getRole().getId());
            }
            duplicatedIds = new ArrayList<>(currentRoles);

            duplicatedIds.retainAll(rolesIds);
            currentRoles.removeAll(duplicatedIds);
            rolesIds.removeAll(duplicatedIds);

            addRoles(id, rolesIds);
            removeRoles(id, currentRoles);
        }

        return rolesIds;
    }

    public String addRoles(Long id, List<Long> roleId) {
        List<MemberRoleEntity> memberRoleEntities = new ArrayList<>();
        Optional<MemberEntity> member = memberRepository.findById(id);
        if (member.isPresent()) {
            for (int i = 0; i < roleId.size(); i++) {
                Optional<RoleEntity> role = roleRepository.findById(roleId.get(i));
                if (role.isPresent()) {
                    memberRoleEntities.add(MemberRoleEntity.builder()
                            .member(member.get())
                            .role(role.get())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
                } else return "Error no role";
            }
        } else {
            return "error no member";
        }

        memberRoleRepository.saveAll(memberRoleEntities);
        return "Successfully added";
    }

    public String removeRoles(Long id, List<Long> roleId) {
        List<MemberRoleEntity> removeArr = new ArrayList<>();
        Optional<MemberEntity> member = memberRepository.findById(id);
        if (member.isPresent()) {
            for (int i = 0; i < roleId.size(); i++) {
                Optional<RoleEntity> role = roleRepository.findById(roleId.get(i));
                if (role.isPresent()) {
                    removeArr.add(memberRoleRepository.findByMemberIdAndRoleId(id, roleId.get(i)).get());
                } else return "Error no role";
            }
        } else {
            return "error no member";
        }

        memberRoleRepository.deleteAllInBatch(removeArr);
        return "Successfully added";
    }

    public Optional<UpdateRequestDto> updateName(UpdateRequestDto request){
        Optional<MemberEntity> optionalMember = memberRepository.findById(request.getId());
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();
            member.changeName(request.getName());
            MemberEntity updatedMember = memberRepository.save(member);
            UpdateRequestDto updateRequestDto = new UpdateRequestDto(updatedMember.getName());
            return Optional.of(updateRequestDto);
        } else {
            return Optional.empty();
        }
    }

    public Optional<UpdateRequestDto> updateCareer(UpdateRequestDto request){
        Optional<MemberEntity> optionalMember = memberRepository.findById(request.getId());
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();
            member.changeCareer(request.getCareer());
            MemberEntity updatedMember = memberRepository.save(member);
            UpdateRequestDto updateRequestDto = new UpdateRequestDto(updatedMember.getCareer());
            return Optional.of(updateRequestDto);
        } else {
            return Optional.empty();
        }
    }
}

