package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class MemberInfoDto {
    private String name;
    private int career;
    private String profileImg;
    private String bio;
    private String githubLink;
    private String blogLink;
    private String notionLink;
    private List<String> roles;
    private List<String> stacks;
}
