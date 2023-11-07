package com.coconet.memberservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
@Schema(name = "Member request", description = "Member request")
public class MemberRequestDto {
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
