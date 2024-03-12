package com.coconet.memberservice.converter;

import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.MemberEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class MemberConverter {

    public static MemberResponseDto toResponseDto(MemberEntity member) {

        List<String> roles = new ArrayList<>();
        if (member.getMemberRoles() != null && !member.getMemberRoles().isEmpty()) {
            roles = member.getMemberRoles().stream().map(role -> role.getRole().getName())
                    .toList();
        }

        List<String> stacks = new ArrayList<>();
        if (member.getMemberStacks() != null && !member.getMemberStacks().isEmpty()) {
            stacks = member.getMemberStacks().stream().map(stack -> stack.getTechStack().getName())
                    .toList();
        }

        return MemberResponseDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(ImageConverter.toImage(member.getProfileImage()))
                .roles(roles)
                .bio(member.getBio())
                .stacks(stacks)
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .build();
    }
}
