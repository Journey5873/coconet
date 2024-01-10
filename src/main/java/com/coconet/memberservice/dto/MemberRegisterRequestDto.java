package com.coconet.memberservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Schema(name = "Member Register Request", description = "same request but with MemberId")
public class MemberRegisterRequestDto {

    @NotNull(message = "Please enter MemberUUID")
    @Schema(example = "qweasd123QDC")
    private UUID memberUUID;
    @NotNull(message = "Please enter user name")
    @Size(min = 2, max = 8, message = "Enter 2~8 lengths of name")
    @Schema(example = "taylor")
    private String name;
    @Min(value = 0, message = "Cannot enter negative career")
    @Max(value = 10, message = "Cannot enter over 10 years career")
    @NotNull(message = "PLease enter career")
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
