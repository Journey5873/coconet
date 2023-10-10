package com.coconet.memberservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
public class MemberRequestDto {
    @Size(min = 2, max = 8)
    private String name;
    @Min(0)
    @Max(10)
    private int career;
    private MultipartFile profileImg;
    @NotNull
    private List<String> roles;
    @NotNull
    private List<String> stacks;
}
