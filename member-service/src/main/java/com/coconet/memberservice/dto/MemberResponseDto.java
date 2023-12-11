package com.coconet.memberservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Schema(name = "Member Response", description = "Member response")
public class MemberResponseDto {
    @Schema(example = "taylor")
    private String name;
    @Schema(example = "3")
    private int career;
    @Schema(example = "member-service/src/main/resources/memberProfilePics/2.png")
    private String profileImg;
    @Schema(example = "Hello, everyone.")
    private String bio;
    @Schema(example = "Tester@github.com")
    private String githubLink;
    @Schema(example = "Tester@blog.com")
    private String blogLink;
    @Schema(example = "Tester@notion.com")
    private String notionLink;
    @Schema(type = "array", example = "[\"Backend\", \"Frontend\"]")
    private List<String> roles;
    @Schema(type = "array", example = "[\"Java\"]")
    private List<String> stacks;
}
