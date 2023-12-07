package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class MemberIdDto {
    private String email;
    private String name;
    private UUID memberUUID;
    private String profileImage;
    private List<MemberRoleResponse> roles;
    private List<MemberStackResponse> stacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
