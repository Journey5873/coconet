package com.coconet.memberservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MemberRegisterRequestDto {

    @NotNull
    private String memberId;
    @Size(min = 2, max = 8)
    private String name;
    private String bio;
    @Min(0)
    @Max(10)
    private int career;
    @NotNull
    private List<String> roles;
    @NotNull
    private List<String> stacks;

    @Max(200)
    private String githubLink;
    @Max(200)
    private String blogLink;
    @Max(200)
    private String notionLink;
}
