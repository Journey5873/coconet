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
    @NotNull(message = "Pleas enter name")
    @Schema(example = "taylor")
    private String name;
    @Min(0)
    @Max(10)
    @NotNull(message = "Please enter career")
    @Schema(example = "3")
    private int career;
    @NotNull(message = "You must have at least one role")
    @NotEmpty(message = "You must have at least one role")
    @Schema(type = "array", example = "[\"Backend\", \"Frontend\"]")
    private List<String> roles;
    @NotNull(message = "You must have at least one stack")
    @NotEmpty(message = "You must have at least one stack")
    @Schema(type = "array", example = "[\"Java\"]")
    private List<String> stacks;
    @Size(max = 50)
    @Schema(example = "Hello, everyone.")
    private String bio;
    @Size(max = 200)
    @Schema(example = "Tester@github.com")
    private String githubLink;
    @Size(max = 200)
    @Schema(example = "Tester@blog.com")
    private String blogLink;
    @Size(max = 200)
    @Schema(example = "Tester@notion.com")
    private String notionLink;
}
