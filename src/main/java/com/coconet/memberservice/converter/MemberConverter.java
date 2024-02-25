package com.coconet.memberservice.converter;

import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.MemberEntity;
import lombok.Builder;

@Builder
public class MemberConverter {

    public static MemberResponseDto toResponseDto(MemberEntity member) {

        return MemberResponseDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(ImageConverter.toImage(member.getProfileImage()))
                .roles(member.getMemberRoles().stream().map(role -> role.getRole().getName())
                        .toList())
                .bio(member.getBio())
                .stacks(member.getMemberStacks().stream().map(stack -> stack.getTechStack().getName())
                        .toList())
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .build();
    }
}
