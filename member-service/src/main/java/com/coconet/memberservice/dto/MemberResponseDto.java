package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
public class MemberResponseDto {
    private String name;
    private int career;
    private MultipartFile profileImg;
    private List<String> roles;
    private List<String> stacks;
}
