package com.coconet.memberservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Schema(name = "Member Register Request", description = "same request but with MemberId")
public class MemberRegisterRequestDto {

    @NotNull
    @Schema(example = "qweasd123QDC")
    private String memberId;
    @Size(min = 2, max = 8)
    @NotNull
    @Schema(example = "taylor")
    private String name;
    @Min(0)
    @Max(10)
    @NotNull
    @Schema(example = "3")
    private int career;
    @NotNull
    @Schema(type = "array", example = "[\"Backend\", \"Frontend\"]")
    private List<String> roles;
    @NotNull
    @Schema(type = "array", example = "[\"Java\"]")
    private List<String> stacks;
    @Size(max = 50)
    @Schema(example = "Hello, everyone.")
    private String bio;
    @Max(200)
    @Schema(example = "Tester@github.com")
    private String githubLink;
    @Max(200)
    @Schema(example = "Tester@blog.com")
    private String blogLink;
    @Max(200)
    @Schema(example = "Tester@notion.com")
    private String notionLink;
}
